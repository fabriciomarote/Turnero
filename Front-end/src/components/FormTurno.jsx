import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from "react-router-dom";
import Service from '../service/service';
import '../styles/FormTurno.css';

const FormTurno = () => {

    const { id, especialidad } = useParams();
    const navigate = useNavigate();
    const [hospital, setHospital] = useState({
        id: "",
        nombre: "",
        direccion: "",
    });
    const [user, setUser] = useState({
        id: "",
        nombreYApellido: "",
        dni: "",
        email: "",
        telefono: "",
    });
    const [turno, setTurno] = useState({
        id: "",
        fechaYHora: "",
        fechaEmitido: "",
        especialidad: "",
        especialista: "",
        hospital: "",
        paciente: "",
    })
    const [sendSMS, setSendSMS] = useState(false);
    const [data, setData] = useState({
        to: "",
        message: "",
    });
    const [showAlertSMS, setShowAlertSMS] = useState(false);
    const [showAlertFecha, setShowAlertFecha] = useState(false);
    const [turnos, setTurnos] = useState([]);
    const [fechaSeleccionada, setFechaSeleccionada] = useState("");
    const isLogged = !!localStorage.getItem("token");

    const fechasDisponibles = () => {
        let fechas = [];
        turnos.map( turno => fechas.push(turno.fechaYHora));
        return fechas;
    };

    const turnoByFecha = (fecha) => {
        turnos.forEach( turno => {
            if( turno.fechaYHora === fecha) {
                setTurno((prevState)=> ({
                    ...prevState,
                    id: turno.id,
                    fechaYHora: turno.fechaYHora,
                    fechaEmitido: turno.fechaEmitido,
                    especialidad: turno.especialidad,
                    especialista: turno.especialista,
                    hospital: turno.hospital,
                    paciente: user
                }))
            }
        });
    }; 

    const changeHandler = (e) => {
        setFechaSeleccionada(e.target.value);
    };
    
    const setMessage = () => {
        setData((prevState)=>({
        ...prevState,
            to: "+54"+`${user.telefono}`,
            message: `Hola, ${user.nombreYApellido}. Usted tiene un turno el ${turno.fechaYHora} para ${turno.especialidad} con el/la especialista ${turno.especialista} en el hospital ${turno.hospital.nombre}`
        }));
    }

    const sendMessage = () => {
        if(sendSMS) {
            Service.postSMS(data)
            .then(_ => {
             setData((prevState)=>({
                 ...prevState,
             }));
        })}
    }

    const handleSubmit = (event) => {
        if (user.telefono === null && sendSMS) {
            setShowAlertSMS(true);
        } else if (fechaSeleccionada === "") {
            setShowAlertFecha(true);
        } 
         else {
            event.preventDefault();
            Service.putActualizarTurno(turno.id, turno)
            .then(_ => {
                setTurno((prevState)=>({
                ...prevState,
                nombreYApellidoPaciente: user.nombreYApellido,
                dniPaciente: user.dni,
                telefonoPaciente: user.telefono,
                emailPaciente: user.email,
                }));
                sendMessage();
                navigate(`/turno/${turno.id}`);
            }).catch(err => 
                console.log(err)
            );
        }
    };
    
    useEffect(() => {
        if (isLogged){
          Service.getUser()
          .then(response => {
            setUser((prevState) => ({
              ...prevState,
              id: response.data.id,
              nombreYApellido: response.data.nombreYApellido,
              dni: response.data.dni,
              email: response.data.email,
              telefono: response.data.telefono,  
            }));
          }).catch(error => {
            console.log(error)
          });
        }}, [isLogged]
    );  
    
    useEffect(() => {
        Service.getHospitalById(id)
            .then(response => { 
                setHospital({
                    id: response.data.id,
                    nombre: response.data.nombre,
                    direccion: response.data.direccion,
                  })      
        }).catch(error => {
            console.log(error)
        });
    }, [id]
    );

    useEffect(() => {
        Service.getTurnosDisponiblesBy(hospital.id, especialidad)
            .then(response => { 
            setTurnos(response.data)
        }).catch(error => {
            console.log(error)
        });
    }, [especialidad, hospital]
    );
    
    useEffect(() => {
        turnoByFecha(fechaSeleccionada);
    }, [fechaSeleccionada, showAlertFecha, showAlertSMS]
    );

    return (
        <>
            <div className="formTurno-container">
                { isLogged 
                ?
                <>
                    <div className='form-content'>    
                        <form onSubmit={handleSubmit}>  
                            <div className="select">
                                <label htmlFor="Name">Turnos Disponibles:</label>
                                <select id="select" value={fechaSeleccionada} onChange={changeHandler}>
                                    <option defaultValue="">Seleccione fecha y horario...</option>
                                    { fechasDisponibles().map (turno => {
                                        return (
                                            <option onClick={() => {setFechaSeleccionada(turno); setShowAlertFecha(true) } } key={turno.id} value={turno} selected={turno} required>{turno}</option>
                                        );
                                    })}
                                </select>
                            </div>
                                <input type="checkbox" onClick={() => {setSendSMS(!sendSMS); setMessage()}}/>
                                <label htmlFor="sms" className='sms'> Recibir Notificación por mensaje de texto</label>
                                { showAlertSMS ? <div className="alert alert-danger" id='alertSMS' role="alert">Debe tener un telefono registrado para poder ser notificado</div> : "" }
                                { showAlertFecha ? <div className="alert alert-danger" id='alertSMS' role="alert">Debe seleccionar una fecha y horario</div> : "" }
                            <div className="turno-button-content">
                                <button type="submit" className="btn-btn btn-info">Confirmar turno</button>
                            </div>
                        </form>     
                    </div>     
                </>    
                :
                <> 
                    <div className='card-notlogged'>
                        <p> Usted debe iniciar sesión para 
                            poder completar el formulario de turno</p>
                    </div>
                </>
                }
            </div>
        </>  
    );
}
  
export default FormTurno;