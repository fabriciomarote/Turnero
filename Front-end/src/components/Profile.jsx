import React, { useState, useEffect }  from 'react'
import { useNavigate } from "react-router-dom"
import Service from '../service/service'
import TurnoModel from './TurnoModel'
import DarDebaja from "./../components/modals/DarDeBaja"
import EditarPerfil from "./../components/modals/EditarPerfil"
import styled from 'styled-components'
import axios from 'axios'
import '../styles/Profile.css'

const Profile = () => {

    const [user, setUser] = useState({
        id: "",
        nombreYApellido: "",
        image: "",
        dni: "",
        email: "",
        telefono: "",
        password: "",
    });
    const [turnos, setTurnos] = useState([]);
    const navigate = useNavigate();
    const isLogged = !!localStorage.getItem("token");
    const [stateModalProfile, setStateModalProfile] = useState(false);
    const [stateModalDelete, setStateModalDelete] = useState(false);

    axios.defaults.headers['authorization'] = localStorage.getItem('token');

    useEffect(() => {
        if (isLogged){
          Service.getUser()
          .then(response => {
            setUser((prevState) => ({
              ...prevState,
              id: response.data.id,
              nombreYApellido: response.data.nombreYApellido,
              image: response.data.image,
              dni: response.data.dni,
              email: response.data.email,
              telefono: response.data.telefono,  
              password: response.data.password
            }));
          }).catch(error => {
            console.log(error)
          });
        } else {
          navigate("/invalid-route");
        }}, [isLogged, navigate]
    );  

    useEffect(() => {
      if (isLogged) {
        Service.getTurnosAsignadosBy(user.id)
        .then(response => {
          setTurnos(response.data)
        })
        .catch(error => {
          console.log(error)
      })}}, [user, isLogged]
    );  


    const logout = () => {
      localStorage.removeItem("userData");
      localStorage.removeItem("token");
      navigate("/") ;
  };

    return (
        <>
          <div className='profile-container'>
            <div className='profile-dates'>
              <div className='image-user'>
                <img src={user.image} className="image" alt="logo"/>
              </div>
              <div className='dates col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                <div className='box-left col-lg-3 col-md-2 col-sm-2 col-xs-2'>
                  <p className='user-name'>{user.nombreYApellido}</p>
                  <div className='info'>
                    <p className='info-title'>DNI: </p>{user.dni}
                  </div>
                  <div className='info'>
                    <p className='info-title'>Telefono: </p>{user.telefono}
                  </div>
                  <div className='info'>
                    <p className='info-title'>Email: </p>{user.email}
                  </div>
                </div> 
                <div className='box-right col-lg-8 col-md-8 col-sm-8 col-xs-8'>
                  <ContenedorBotones>
                    <Boton onClick={() => setStateModalProfile(!stateModalProfile)}>Editar Perfil</Boton>
                  </ContenedorBotones>      
                  <EditarPerfil user={user} setUser={setUser} state={stateModalProfile} setState={setStateModalProfile}/>           
                  <ContenedorBotones>
                    <Boton onClick={() => setStateModalDelete(!stateModalDelete)}>Eliminar Cuenta</Boton>
                  </ContenedorBotones>      
                  <DarDebaja user={user} setUser={setUser} state={stateModalDelete} setState={setStateModalDelete}/> 
                  <Boton onClick={() => logout()}> Cerrar Sesión </Boton>
                </div>  
              </div>
            </div>
            <div className='profile-turnos'>
              <h4>Mis turnos:</h4>
              {turnos.map((turno, idx) => {          
                return <TurnoModel key={turno.id} turno={turno}/>
              })}
            </div>
          </div>
        </>  
    );
};
  
export default Profile;

const ContenedorBotones = styled.div`
    display: flex;
    flex-wrap: wrap;
    justify-content:center;
    gap: 20px;
  `;

  const Boton = styled.button`
    display: block;
    width: 120px;
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
    @media (max-width:600px) {
      height: 20px;
      width: 100px;
      font-size: 10px;
      font-weight: 900;
    }
  `;