import React, { useState } from 'react'
import styled from 'styled-components';
import Service from './../../service/service.js'
import axios from 'axios';
import './../../styles/Register.css';

const Register = ({state, setState, setStateLogin }) => {

  const [data, setData] = useState({
    nombreYApellido: "",
    image: "",
    dni: "",
    email: "",
    telefono: "",
    password: "",
    turnosAsignados: []
  });

  const [registerError, setRegisterError] = useState (false);
  const [registerErrorName, setRegisterErrorName] = useState("");

  const handleChange = name => event => {
    setData(prevState => ({ ...prevState, [name]: event.target.value }));
  };

  axios.defaults.headers['authorization'] = localStorage.getItem('token');

  const handleSubmit = (event) =>{
    event.preventDefault(); 
    Service.postRegister(data)
      .then(response => {
        localStorage.setItem("token", response.data.token);
        window.location.reload();  
      })
      .catch(err => {
        setRegisterError(true)
        setRegisterErrorName(err.response.data.message);  
      })
  };

  return ( 
    <>
      { state && 
        <Overlay>
          <ContenedorModal>
            <EncabezadoModal>
              <h3 className="form-title">REGISTRATE EN TURNERO</h3>
            </EncabezadoModal>
            <BotonCerrar onClick={() => setState(!state)}>
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
              </svg>
            </BotonCerrar> 
            <Contenido>
              <div className="register-container">
                <div className="form-register-card" > 
                  <form className='formModal' onSubmit={handleSubmit}>
                    <div className='modal-inputs-register'>
                      <label>Nombre y Apellido</label>
                      <input className="form-input" type='text' name="NombreYApellido" value={data.nombreYApellido} onChange={handleChange("nombreYApellido")} placeholder="Roberto Gomez" required></input>
                      <label>DNI</label>
                      <input className="form-input" type='text' name="dni" value={data.dni} onChange={handleChange("dni")} placeholder="20345678" required></input>
                      <label>Email</label>
                      <input className="form-input" type='email' name="email" value={data.email} onChange={handleChange("email")} placeholder="example@gmail.com" required></input>
                      <label>Contraseña</label>
                      <input className="form-input" type='password' name="password" value={data.password} onChange={handleChange("password")} placeholder="Debe contener al menos 8 caracteres" required></input> 
                    </div>
                    {registerError && (<div id='alert-register' className="alert alert-danger" role="alert">{registerErrorName}</div>) }
                    <button type="submit" className="btn-info b-register">REGISTRARSE</button>
                  </form>
                </div>    
                <div className='modalFooter-register'>
                  ¿Ya sos usuario? <Boton onClick={() => { setState(false); setStateLogin(true)}}>INICIA SESIÓN</Boton>
                </div>
              </div>
            </Contenido>  
          </ContenedorModal>
        </Overlay>
      }
    </>
  )
}

export default Register;

  const Overlay = styled.div`
    width: 100vw;
    height: 100vh;
    position: fixed;
    top:0;
    left:0;
    background: rgba(0,0,0,.9);    
    padding: 40px;
    display: flex;
    align-items: center;
    justify-content: center;  
    transition: .3s ease all;
  `;

  const ContenedorModal = styled.div`
    width: 500px;
    height: 680px;
    background: rgba(24, 22, 80, 0.7);
    position: relative;
    display: grid;
    border-radius:10px;
    box-shadow: rgba(100,100,11, 0.2) 0px 7px 29px 0px;
    padding: 50px;
    transition: .3s ease all;
    animation: show 1s .5s;
    @media (max-width:600px) {
    width: 500px;
    height: 600px;
  }
  `;

  const EncabezadoModal = styled.div`
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 5px;
    padding-bottom: 5px;
    border-bottom: 1px solid #E8E8E8;
    h3 {
      font-weight: 800;
      font-size: 30px;
      color: #E8E8E8;
      text-indent: 0px;
      @media (max-width:600px) {
        font-size: 20px;
      }
    }
  `;

  const BotonCerrar = styled.div`
    position: absolute;
    top: 17px;
    right: 10px;
    width: 30px;
    height: 30px;
    border: none;
    background: none;
    cursor: pointer;
    transition: .3s ease all;
    border-radius: 5px;
    color: #E8E8E8;
    svg {
      width: 90%;
      height: 90%;
    }
  `;

  const Contenido = styled.div`
  `;

  const Boton = styled.button`
    display: block;
    width: 150px;
    height: 40px;
    border: none;
    color: #fff;
    border: none;
    margin: 10px;
    font-size: 17px;
    font-weight: 900;
    background-color: transparent;
    cursor: pointer;
    font-family: 'Roboto', sans-serif;
    font-weight: 900;
    transition: .3s ease all;
    &:hover {
      color: #26B5A8;
    }
    @media (max-width:600px) {
      height: 30px;
      width: 140px;
      font-size: 14px;
      margin-bottom: 8px;
  }
  `;