import React, { useState, useEffect }   from 'react';
import { useParams } from "react-router-dom";
import Service from '../service/service';
import EspecialidadModel from './EspecialidadModel';
import '../styles/Hospital.css';

const Hospital = () => {

    const { id } = useParams();
    
    const [hospital, setHospital] = useState({
        id: "",
        nombre: "",
        direccion: "",
        municipio: "",
        especialidades: [],
    });

    useEffect(() => {
        Service.getHospitalById(id)
            .then(response => { 
                setHospital({
                    id: response.data.id,
                    nombre: response.data.nombre,
                    direccion: response.data.direccion,
                    municipio: response.data.municipio,
                    especialidades : response.data.especialidades
                  })
        }).catch(error => {
            console.log(error)
        });
    }, [id]
    );

    return (
        <>
            <div className="hospital-container">
                <div className='title-hospital'>
                    {hospital.nombre}, especialidades disponibles:
                </div>
                <div className='especialidades-hospital-box'>            
                    {hospital.especialidades.map((especialidad, idx) => {          
                        return <EspecialidadModel key={idx} especialidad={especialidad} hospital={hospital}/>
                    })}
                </div>   
            </div>
        </>  
    );
  }
  
  export default Hospital;