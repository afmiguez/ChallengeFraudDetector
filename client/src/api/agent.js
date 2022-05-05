import axios from "axios";


axios.defaults.baseURL = 'http://localhost:8080';
axios.interceptors.request.use(config => {
    const token=sessionStorage.getItem("token");
    console.log()
    if(token){
        config.headers.Authorization=`Bearer ${token}`;
    }
    return config;
})

axios.interceptors.response.use(async response => {
    return response;
},(error)=>{
    const {data, status, config} = error.response;

})

const responseBody = response => response.data;

const requests={
    get: (url)=>axios.get(url).then(responseBody),
    post: (url,body)=>axios.post(url,body).then(responseBody),
    patch: (url,body)=>axios.patch(url,body).then(responseBody),
    put: (url,body)=>axios.put(url,body).then(responseBody),
    delete: (url)=>axios.delete(url).then(responseBody),
}

const Transactions={
    get:()=>requests.get(`/transactions`),

    add:(formData)=>requests.post("/upload",formData),
    getSuspicious:(month)=>requests.get(`/transactions/suspicious/${month}`)
}

const Imports={
    get:()=>requests.get(`imports`),
    getById:(id)=>requests.get(`/imports/${id}`),
}

const Users={
    login:(credentials)=>requests.post(`/login`,credentials),
    get:()=>requests.get(`/users`),
    delete:(id)=>requests.delete(`/users/${id}`)
}

const agent={
    Transactions,
    Imports,
    Users,
}

export default agent