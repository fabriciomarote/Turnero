import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; 
import Service from '../service/service';
import '../styles/EspecialidadModel.css';

const EspecialidadModel = (props) => {

  const { especialidad, hospital} = props;

  const [turnos, setTurnos] = useState([]);

  const navigate = useNavigate();
  const goFormTurno = () => {
      if (turnos.length > 0) {
        navigate(`/hospital/${hospital.id}/${especialidad}/sacar-turno`) ;
      } 
  };
  
  useEffect(() => {
    Service.getTurnosDisponiblesBy(hospital.id, especialidad)
      .then(response => { 
      setTurnos(response.data)
    }).catch(error => {
      console.log(error)
    });
  }, [especialidad, hospital]
  );

    return (
        <>
          <div className="especialidad-container" onClick={() => goFormTurno()}>
            <div className="block-pad block-btn-green">
              <p className="btn">{especialidad}</p> 
              { turnos.length=== 0 ?<p className= "alert-turnos">¡Sin turnos disponibles!</p> : <p></p> }
            </div>
          </div>
        </>
    );
  }

  export default EspecialidadModel;