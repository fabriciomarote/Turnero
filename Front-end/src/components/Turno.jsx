import React, { useState, useEffect } from 'react';
import { useParams } from "react-router-dom";
import Service from '../service/service';
import Navbar from '../components/Navbar';
import '../styles/Turno.css';

const Turno = () => {

    const { id } = useParams();
    const [turno, setTurno] = useState({
        id: "",
        nombreYApellidoPaciente: "",
        dniPaciente: "",
        telefonoPaciente: "",
        emailPaciente: "",
        fechaYHora: "",
        fechaEmitido: "",
        especialidad: "",
        especialista: "",
        nombreHospital: "",
        direccionHospital: "",
    })

    useEffect(() => {
        Service.getTurnoById(id)
            .then(response => { 
                setTurno({
                    id: response.data.id,
                    nombreYApellidoPaciente: response.data.paciente.nombreYApellido,
                    dniPaciente: response.data.paciente.dni,
                    telefonoPaciente: response.data.telefonoPaciente,
                    emailPaciente : response.data.emailPaciente,
                    fechaYHora: response.data.fechaYHora,
                    fechaEmitido: response.data.fechaEmitido,
                    especialidad: response.data.especialidad,
                    especialista: response.data.especialista,
                    nombreHospital: response.data.hospital.nombre,
                    direccionHospital: response.data.hospital.direccion,
                  })      
        }).catch(error => {
            console.log(error)
        });
    }, [id]
    );

    return (
        <>
            <div className='turno-container'>
                <div className="title-turno">
                    <p>Su turno ha sido confirmado</p>
                </div>
                <div className="card-turno">
                    <div className="box-turno">
                        <div className="card-header">
                            <div className='header-left'>
                                <p className='name-hospital'> {turno.nombreHospital} </p>
                                <p>{turno.direccionHospital}</p>
                            </div>
                            <div className='header-right'>
                                <p>CÓDIGO DEL TURNO: {turno.id}</p>
                            </div>
                        </div>
                        <div className="card-body">
                            <div className="body-left">
                                    <p>FECHA: {turno.fechaYHora} </p>
                                    <p className='especialidad-turno'>ESPECIALIDAD: {turno.especialidad}</p>
                                    <p>PROFESIONAL: {turno.especialista}</p>
                                    <p className='fecha-turno'>FECHA EMITIDO: {turno.fechaEmitido}</p> 
                            </div>
                            <div className='body-right'>
                                    <p>SI NO PUEDES ASISTIR AL TURNO EN EL DIA Y 
                                        HORARIO ASIGNADO, POR FAVOR CANCELALO.</p>
                            </div>
                        </div>
                        <div className="footer-turno">
                            <div className="paciente-date">
                                <p>PACIENTE: {turno.nombreYApellidoPaciente} </p>
                                <p>DNI: {turno.dniPaciente}</p>
                            </div>
                        </div>
                    </div>   
                </div>
            </div> 
        </> 
    );
  }
  
  export default Turno;