import { BrowserRouter, Route, Routes } from 'react-router-dom';
import React from 'react';
import Home from './components/Home';
import Search from './components/Search';
import Hospital from './components/Hospital';
import FormTurno from './components/FormTurno';
import Turno from './components/Turno';
import Profile from './components/Profile';
import InvalidRoute from './components/InvalidRoute';
import Hospitales from './components/Hospitales';
import Navbar from './components/Navbar';

const App = () => {

  return (
    <BrowserRouter>
      <div className="navbar">
        <Navbar/>
      </div> 
      <Routes>
        <Route exact path="/" element={<Home/>} />
        <Route path="/hospital/search" element={<Search/>}/>
        <Route path="/hospital/:id/especialidades" element={<Hospital/>}/>
        <Route path="/hospital/:id/:especialidad/sacar-turno" element={<FormTurno/>}/>
        <Route path="/turno/:id" element={<Turno/>}/>
        <Route path="/profile" element={<Profile/>}/>
        <Route path="/hospitales" element={<Hospitales/>}/>
        <Route path="*" element={<InvalidRoute/>} />
        <Route path="/invalid-route" element={<InvalidRoute/>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;