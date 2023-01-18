import React, { useState } from 'react'
import styled from 'styled-components';
import Service from './../../service/service.js'
import './../../styles/EditProfile.css';

const EditarPerfil = ({user, setUser, state, setState}) => {

  const [editError, setEditError] = useState(false);
  const [editErrorName, setEditErrorName] = useState("");

  const handleChange = name => event => {
      setUser(prevState => ({ ...prevState, [name]: event.target.value }));
  };

  const handleChangeImage = name => event => {
    setUser(prevState => ({ ...prevState, [name]: event.target.value.slice(event.target.value.lastIndexOf("\\")+1)}));
};

  const handleSubmit = (event) =>{
    Service.putActualizarPerfil(user.id, user)
      .then(_ => {
        setUser((prevState)=>({
          ...prevState,
          image: user.image,
        }));
        setState(!state);
        window.location.reload();  
      })
      .catch(err => {
        setEditError(true)
        setEditErrorName(err.response.data.message);  
    })
  };

  return ( 
    <>
      { state && 
        <Overlay>
          <ContenedorModal>
            <EncabezadoModal>
                <h2>Editar Perfil</h2>
            </EncabezadoModal>
            <BotonCerrar onClick={() => setState(!state)}>
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-x" viewBox="0 0 16 16">
                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
              </svg>
            </BotonCerrar> 
            <Contenido>
              <div>
                <label className='name-user'>Nombre y Apellido</label>
                <input type="text" className="form-input-edit" value={user.nombreYApellido} onChange={handleChange("nombreYApellido")}/>
                <label>Imagen</label>
                <input type="text" className="form-input-edit" value={user.image} onChange={handleChange("image")}/>
                <label>Email</label>
                <input type="text" className="form-input-edit" value={user.email} onChange={handleChange("email")}/>
                <label>Telefono</label>
                <input type="text" className="form-input-edit" value={user.telefono} onChange={handleChange("telefono")}/>
                <label>Contraseña</label>
                <input type="password" className="form-input-edit" value={user.password} onChange={handleChange("password")}/>
                <div align="center">
                  { editError && (<div id='alert-edit' className="alert alert-danger" role="alert">{editErrorName}</div>) }
                </div>
              </div>
              <BotonesConfirmacion>
                <Boton onClick={() => handleSubmit()}>Aceptar</Boton>
                <Boton onClick={() => setState(!state)}>Cancelar</Boton>
              </BotonesConfirmacion>
            </Contenido>
          </ContenedorModal>
        </Overlay>
      }
    </>
  )
}

export default EditarPerfil;

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
  `;

  const ContenedorModal = styled.div`
    width: 500px;
    min-height: 150px;
    background: rgba(24, 22, 80, 0.9);
    position: relative;
    border-radius:5px;
    box-shadow: rgba(100,100,11, 0.2) 0px 7px 29px 0px;
    padding: 20px;
    padding-left: 40px;
    @media (max-width:600px) {
      height: 550px;
      width: 500px;
    }
  `;

  const EncabezadoModal = styled.div`
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 20px;
    padding-bottom: 20px;
    border-bottom: 1px solid #E8E8E8;
    h2 {
      font-weight: 500;
      font-size: 28px;
      color: white(0,0,0);
      text-indent: 0px;
      @media (max-width:600px) {
        font-weight: 700;
        font-size: 20px;
    }
    }
  `;

  const Boton = styled.button`
    display: block;
    width: 130px;
    height: 40px;
    border-radius: 10px;
    color: #fff;
    border: none;
    margin: 20px;
    font-size: 15px;
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
      height: 35px;
      width: 100px;
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
    color: white(0,0,0);
    svg {
      width: 80%;
      height: 80%;
    }
  `;

  const BotonesConfirmacion = styled.div`
    display: flex;
    flex-direction: row;
    justify-content:center;
    align-items: center;
  `;

  const Contenido = styled.div`
  text-indent: 10px;
    @media (max-width:600px) {
        font-weight: 500;
        font-size: 12px;
        text-indent: 10px
    }
  `;

  //<input type="file" className="form-input2" onChange={handleChangeImage("image")}/>