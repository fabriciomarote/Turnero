import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; 
import Service from '../service/service';
import '../styles/HospitalModel.css';

const HospitalModel = (props) => {

    const { hospital, busqueda, value } = props

    const [turnos, setTurnos] = useState([]);

    const navigate = useNavigate();

    const goFormTurnoOrEspecialidadesIf = () => {
        if (value === "nombre" || value === "municipio") {
            navigate(`/hospital/${hospital.id}/especialidades`) ;
        } else if (value === "especialidad" && turnos.length > 0) {
            navigate(`/hospital/${hospital.id}/${busqueda}/sacar-turno`) ;
        }
    };

    useEffect(() => {
        Service.getTurnosDisponiblesBy(hospital.id, busqueda)
            .then(response => { 
            setTurnos(response.data)
        }).catch(error => {
            console.log(error)
        });
    }, [hospital, busqueda]
    );

    return (
        <>
            <div className="hospitalModel-container" onClick={() => goFormTurnoOrEspecialidadesIf()}>  
                <div className='box-image'>
                </div>
                <div className='box-description'>
                    <div className="name-selection">  
                        <p className="selection-name" >{hospital.nombre}</p> 
                        { value === "especialidad" && turnos.length === 0 ?<p className= "alert-turnos-2">¡Sin turnos disponibles!</p> : <p></p> }
                    </div>
                </div>
            </div>
        </>
    );
};
  
export default HospitalModel;