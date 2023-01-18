import React, { useState } from 'react';
import { BsPersonCircle } from "react-icons/bs";
import logo from '../logo-simple.png';
import Register from "./modals/Register";
import Login from "./modals/Login";
import axios from 'axios';
import styled from 'styled-components';
import '../styles/Navbar.css';

const Navbar = () => {

    const [stateModalRegister, setStateModalRegister] = useState(false); 
    const [stateModalLogin, setStateModalLogin] = useState(false);   

    const buttonsLogueado = () => {
        return(
            <>
                <div className="buttons-content">
                    <a className="button-nb" href={`/profile`}>PERFIL <BsPersonCircle className='icon-profile'/></a>
                </div>
            </>
        )
    };

    axios.defaults.headers['authorization'] = localStorage.getItem('token');
      
    const buttonsSinLoguearse = () => {
        return(
          <>
            <Boton1 onClick={() => setStateModalRegister(!stateModalRegister)}>REGISTRARSE</Boton1>
            <Register state={stateModalRegister} setState={setStateModalRegister} setStateLogin={setStateModalLogin}/>
            <Boton2 onClick={() => setStateModalLogin(!stateModalLogin)}>INICIAR SESIÓN</Boton2>
            <Login state={stateModalLogin} setState={setStateModalLogin} setStateRegister={setStateModalRegister}/>
          </>  
        )
    };

    const Buttons = !!localStorage.getItem("token") ? buttonsLogueado : buttonsSinLoguearse;

    return (
        <>
            <div className="navbar-container">
                <div className="nav">
                    <input type="checkbox" id="nav-check"/>
                    <div className="nav-btn">
                        <label for="nav-check">
                            <span></span>
                            <span></span>
                            <span></span>
                        </label>
                    </div>
                    <div className="nav-links">
                        <Buttons/>
                        <span className='separador'>|</span>
                        <a className="button-nb" href={`/`} id="btn"> Inicio </a>
                        <a className="button-nb" href={`/hospitales`} id="btn"> Hospitales Adheridos</a>   
                    </div>
                    <div className="nav-header">
                        <div className="box-logo">  
                        <a title="logo" href="/"><img src={logo} alt="logo" height="80" width="80"/></a>
                        </div>
                    </div>
                    
                </div>
            </div>
        </>
    )
  }
  
  export default Navbar;

  const Boton1 = styled.button`
    display: block;
    width: 150px;
    height: 19px;
    border: none;
    color: #fff;
    border: none;
    margin: 5px;
    font-size: 13px;
    font-weight: 900;
    background-color: transparent;
    cursor: pointer;
    font-family: 'Roboto', sans-serif;
    font-weight: 900;
    transition: .3s ease all;
    &:hover {
      color: #A9D35A;
    }
    @media (max-width:600px) {
    width: 200px;
    color: white;
    margin: 20px;
    font-size: 20px;
    font-weight: 900;
    text-transform: uppercase;
    margin-left:-2px;
    margin-bottom: 50px;
  }
  `;
  const Boton2 = styled.button`
    display: block;
    width: 150px;
    height: 19px;
    border: none;
    color: #fff;
    border: none;
    margin: 5px;
    font-size: 13px;
    font-weight: 900;
    background-color: transparent;
    cursor: pointer;
    font-family: 'Roboto', sans-serif;
    font-weight: 900;
    transition: .3s ease all;
    &:hover {
      color: #A9D35A;
    }
    @media (max-width:600px) {
    width: 200px;
    color: white;
    margin: 20px;
    font-size: 20px;
    font-weight: 900;
    text-transform: uppercase;
    margin-left:8px;
    margin-bottom: 40px;
  }
  `;