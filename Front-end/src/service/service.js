import axios from "axios"

export const Service = {
    getHospitalById: function(id) {return axios.get(`http://localhost:8080/hospital/${id}`)},
    getHospitales: function() {return axios.get(`http://localhost:8080/hospital`)},
    getSearch: function(data, select) {return axios.get(`http://localhost:8080/hospital/search?q=${data}&value=${select}`)},
    getTurnosDeHospital(id) { return axios.get(`http://localhost:8080/turno/todos/${id}`)},
    getTurnosDisponiblesBy(id, especialidad) {return axios.get(`http://localhost:8080/turno/todos/${id}/${especialidad}`)},
    getTurnoById: function(id) {return axios.get(`http://localhost:8080/turno/${id}`)},
    putActualizarTurno: function(id, data) {return axios.put(`http://localhost:8080/turno/${id}`, data)},
    postRegister: function(data) { return axios.post(`http://localhost:8080/usuario/register`, data)},
    postLogin: function(data) { return axios.post(`http://localhost:8080/usuario/login`, data)},
    getUser: function() { return axios.get(`http://localhost:8080/usuario`)},
    postSMS: function(data) { return axios.post(`http://localhost:8080/sms`, data)},
    deleteUser: function(id) { return axios.delete(`http://localhost:8080/usuario/${id}`)},
    getTurnosAsignadosBy(dni) { return axios.get(`http://localhost:8080/turno/usuario/${dni}`)},
    putActualizarPerfil: function(id, data) {return axios.put(`http://localhost:8080/usuario/${id}`, data)},
    deleteTurno: function(id) { return axios.delete(`http://localhost:8080/turno/${id}`)},
}

export default Service;