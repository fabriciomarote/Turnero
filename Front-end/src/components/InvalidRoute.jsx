import React from 'react'
import { useNavigate } from 'react-router-dom'
import { TbError404 } from "react-icons/tb";
import '../styles/InvalidRoute.css'

 const InvalidRoute = () => {

    const navigate = useNavigate()

    return (
        <div className='invalid'>
            <div>
            <TbError404 className='error404'/>
            <h1>PÁGINA NO ENCONTRADA</h1>
            <h5>No se ha podido encontrar la página solicitada</h5>
            <div>
                <button id='btn-invalid' className='btn-btn btn-info' onClick={ ()=> navigate('/')}>Volver al inicio</button>
            </div>
            </div>
        </div>
    )
}

export default InvalidRoute;    