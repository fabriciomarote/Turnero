import React, { useState } from "react"
import styled from 'styled-components'
import CancelarTurno from "./modals/CancelarTurno"
import '../styles/TurnoModel.css'


const TurnoModel = (props) => {

  const { turno } = props
  const [stateModal, setStateModal] = useState(false);

  return (
      <>  
        <div className="turnoModel-container">
          <div className='card-turno'>
            <p className="value-turno" id="codigo"><strong>Codigo:</strong> {turno.id}</p> 
            <p className="value-turno" id="especialidad"><strong>Especialidad:</strong> {turno.especialidad}</p>
            <p className="value-turno" id="especialista"><strong>Profesional:</strong> {turno.especialista}</p>
            <p className="value-turno"><strong>Fecha y Hora:</strong> {turno.fechaYHora}</p>
            <p className="value-turno"><strong>Hospital:</strong> {turno.nombreHospital}</p>  
            <ContenedorBotones>
              <Boton onClick={() => setStateModal(!stateModal)}>Cancelar</Boton>
            </ContenedorBotones>      
            <CancelarTurno state={stateModal} setState={setStateModal} turno={turno}/> 
          </div>
        </div>
      </>
    );
};
  
export default TurnoModel;

  const ContenedorBotones = styled.div`
    display: flex;
    flex-wrap: wrap;
    justify-content:center;
    gap: 20px;
  `;

  const Boton = styled.button`
    display: block;
    width: 70px;
    height: 30px;
    border-radius: 10px;
    color: #fff;
    border: none;
    margin: 5px;
    font-size: 12px;
    border-color:#A9D35A;
    background-color:#A9D35A;
    color:black;
    cursor: pointer;
    font-family: 'Roboto', sans-serif;
    font-weight: 700;
    transition: .3s ease all;
    &:hover {
      border-color:#26B5A8;
      background-color:#26B5A8;
      color: white;
    }
  `;
