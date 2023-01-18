import React, { useState, useEffect } from "react";
import { useLocation } from 'react-router-dom';
import Service from '../service/service';
import HospitalModel from './HospitalModel';
import '../styles/Search.css';

const Search = () => {

    const location = useLocation();
    const search = location.search.slice(3, location.search.indexOf("&"));
    const value = location.search.slice(location.search.indexOf("&")+7);
    const [hospitales, setHospitales] = useState([]);

    useEffect(() => {
        if (search.length !== 0) {
            Service.getSearch(search, value)
                .then(response => { 
                setHospitales(response.data)
            }).catch(error => {
                console.log(error)
            });
        }    
    }, [search, value]
    );

    return (
        <>
            <div className="search-container">
                <div className="content">  
                    <div className="text-result">
                        <p>Resultado por {value}: {hospitales.length} </p>
                    </div>
                    <div>    
                        { hospitales.length > 0 ?
                            <div className="box-result">
                                {hospitales.map((hospital, idx) => {          
                                    return <HospitalModel key={hospital.id} hospital={hospital} value={value} busqueda={search}/>
                                })}
                            </div>
                        :    
                            <div className="text-result">
                                <h6> No se han encontrado resultados para tu busqueda</h6>
                            </div>
                        }     
                    </div>
                </div>
            </div>
        </>  
    ); 
}

export default Search;