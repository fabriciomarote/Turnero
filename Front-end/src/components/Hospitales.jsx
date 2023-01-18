import React, { useState, useEffect } from 'react';
import Service from '../service/service';
import '../styles/Hospitales.css';

const Hospitales = () => {
    
    const [hospitales, setHospital] = useState([]);

    useEffect(() => {
        Service.getHospitales()
            .then(response => { 
                setHospital(response.data)
        }).catch(error => {
            console.log(error)
        })
    }, []
    );

    return (
        <>
            <div className="hospitales-container">
                <div className='hospitales-box'>            
                    {hospitales.map((hospital, idx) => {          
                        return(
                            <div className="hospitales-container">
                                <div className="block-pad-hospital">
                                    <p className="hospital-name">{hospital.nombre} - {hospital.municipio} </p> 
                                </div>
                            </div>
                        )
                    })}
                </div>   
            </div>
        </>  
    );
  };
  
  export default Hospitales;