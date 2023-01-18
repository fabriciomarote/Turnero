import React, { useState } from 'react';
import styled from 'styled-components';
import Service from './../../service/service.js'
import axios from 'axios';
import './../../styles/Login.css';

const Login = ({state, setState, setStateRegister }) => {

  const [data, setData] = useState({
      email: "",
      password: ""
  });

  const [loginError, setLoginError] = useState (false);
  const [loginErrorName, setLoginErrorName] = useState("");

  axios.defaults.headers['authorization'] = localStorage.getItem('token');

  const handleChange = name => event => {
    setData(prevState => ({ ...prevState, [name]: event.target.value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    Service.postLogin(data)
      .then(response => {
        console.log(response)
        localStorage.setItem("token", response.data.token);
        window.location.reload();  
        })
      .catch(err => {
        setLoginError(true)
        setLoginErrorName(err.response.data.message);  
      })
  };

  return ( 
    <>
      { state && 
        <Overlay>
          <ContenedorModal>
            <EncabezadoModal>
              <h3 className="form-title">INICIAR SESIÓN</h3>
            </EncabezadoModal>
            <BotonCerrar onClick={() => setState(!state)}>
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
              </svg>
            </BotonCerrar> 
            <Contenido>
                <div className="modal-body">
                    <div className='modalForm'>
                        <form className='formModal' onSubmit={handleSubmit}>
                            <div className='modal-inputs'>
                                <input className="form-input-login" type='text' name="email" value={data.email} onChange={handleChange("email")} placeholder="Email" required  ></input>
                                <input className="form-input-login" type='password' name="password" value={data.password} onChange={handleChange("password")} placeholder="Contraseña" required></input> 
                            </div>
                            { loginError && (<div id='alert-login' className="alert alert-danger" role="alert">{loginErrorName}</div>) }
                            <button type="submit" className="btn-info b-log">INICIAR SESIÓN</button>
                        </form>
                    </div>
                    <div className='modalFooter-login'>
                        ¿Todavia no sos usuario? <Boton className="button-nb" onClick={() => { setState(false); setStateRegister(true)}}>Registrate</Boton>
                    </div>
                </div>
            </Contenido>  
          </ContenedorModal>
        </Overlay>
      }
    </>
  );
};

export default Login;

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
    min-height: 100px;
    background: rgba(24, 22, 80, 0.7);
    position: relative;
    display: grid;
    border-radius:10px;
    box-shadow: rgba(100,100,11, 0.2) 0px 7px 29px 0px;
    padding: 50px;
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
  font-size: 18px;
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
      width: 120px;
      font-size: 14px;
      margin-bottom: 8px;
  }
`;