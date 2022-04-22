import axios from "axios";


axios.defaults.baseURL = 'http://localhost:8080';
axios.interceptors.request.use(
    config => {
        return config
        //configure token
    }
)

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
    add:(formData=new FormData())=>requests.post("/upload",formData)
}

const Imports={
    get:()=>requests.get(`imports`)
}

const agent={
    Transactions,
    Imports,
}

export default agent