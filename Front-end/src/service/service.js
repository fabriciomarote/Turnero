import axios from "axios"

const API = (process.env.REACT_APP_API_URL || "http://localhost:8080").replace(/\/$/, "")

export const Service = {
    getHospitalById: function(id) {return axios.get(`${API}/hospital/${id}`)},
    getHospitales: function() {return axios.get(`${API}/hospital`)},
    getSearch: function(data, select) {return axios.get(`${API}/hospital/search?q=${data}&value=${select}`)},
    getTurnosDeHospital(id) { return axios.get(`${API}/turno/todos/${id}`)},
    getTurnosDisponiblesBy(id, especialidad) {return axios.get(`${API}/turno/todos/${id}/${especialidad}`)},
    getTurnoById: function(id) {return axios.get(`${API}/turno/${id}`)},
    putActualizarTurno: function(id, data) {return axios.put(`${API}/turno/${id}`, data)},
    postRegister: function(data) { return axios.post(`${API}/usuario/register`, data)},
    postLogin: function(data) { return axios.post(`${API}/usuario/login`, data)},
    getUser: function() { return axios.get(`${API}/usuario`)},
    postSMS: function(data) { return axios.post(`${API}/sms`, data)},
    deleteUser: function(id) { return axios.delete(`${API}/usuario/${id}`)},
    getTurnosAsignadosBy(dni) { return axios.get(`${API}/turno/usuario/${dni}`)},
    putActualizarPerfil: function(id, data) {return axios.put(`${API}/usuario/${id}`, data)},
    deleteTurno: function(id) { return axios.delete(`${API}/turno/${id}`)},
}

export default Service;
