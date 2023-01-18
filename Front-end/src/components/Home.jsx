import React, { useState }  from 'react';
import { useNavigate } from 'react-router-dom';
import { AiOutlineSearch } from "react-icons/ai";
import logo from '../logo2.png';
import Loading from '../components/Loading';
import '../styles/Home.css';

const Home = () => {

    const [selection, setSelection] = useState(""); 
    const [search, setSearch] = useState("");
    const [showAlert, setShowAlert] = useState(false);
    const [loading, setLoading] = useState(false);
    const changeSearch = (event) => setSearch(event.target.value);
    const navigate = useNavigate();

    const changeSelectValue = (value) => {
        setSelection(value)
        setShowAlert(false)
    };

    const changeState = () => {
        setLoading(true);
        setTimeout(() => {
            setLoading(false);
            goSearchResults();
        }, 1000);
    };

    const goSearchResults = () => {
        navigate("/hospital/search?q=" + search + "&value=" + selection);
    };

    const handleButtonClick = () => {
        if (selection === "") {
            setShowAlert(true)
        } else {
            changeState();
        }
    };

    if (!loading) {
        return (
        <>
            <div className="home-container">
                <div className="box-container">
                    <div>
                        <img className='logo-home' src={logo} alt="logo"/>
                    </div>
                    <div className="search-content">  
                        <div className='search-bar'>
                            <div className='message-error'>
                                { showAlert ? <div className="alert alert-warning" role="alert">Debe seleccionar una opción para realizar la busqueda</div> : ""}
                            </div>
                            <div className='box-search'>
                                <input className="search-btn" type="text" placeholder="Buscar hospital" aria-label="Search" onChange={changeSearch} />
                                <button onClick={() => handleButtonClick(search, selection)} className="btn btn-secondary" type="submit"><AiOutlineSearch color="white" size={25}/></button>
                            </div>
                        </div>
                        <div className='search-checkbox'>
                            <input type="radio" className="radio" name="1" onClick={ () => changeSelectValue("nombre")} required/><label className='checkbox'>Nombre</label>
                            <input type="radio" className="radio" name="1" onClick={ () => changeSelectValue("municipio")} required/><label className='checkbox'>Municipio</label>
                            <input type="radio" className="radio" name="1" onClick={ () => changeSelectValue("especialidad")} required/><label className='checkbox'>Especialidad</label>
                        </div>
                    </div>
                </div>  
            </div>
            <div className='footer'>
                <span className="copyleft">&copy;</span>{ new Date().getFullYear() }{" | Desarrollado para Elementos de Ingeniería de Software, UNQ."}
            </div>  
        </>  
        );
    } else {
        return ( 
            <>
               <div className="home-loading">
                    <div className='loading'>
                        <Loading/>
                    </div>
               </div>
            </> 
        )
    }
}
  
export default Home;