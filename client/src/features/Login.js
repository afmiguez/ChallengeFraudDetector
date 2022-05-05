import {useState} from "react";
import agent from "../api/agent";
import {Button, Container, Form, FormControl, FormLabel} from "react-bootstrap";

const Login = ({setIsLoggedIn}) => {

    const [email, setEmail] = useState('admin@email.com.br')
    const [password, setPassword] = useState('123999')

    const {Users}=agent

    const handleLogin=async ()=>{
        const loginResult=await Users.login({email,password})
        if(loginResult){
            sessionStorage.setItem("token",loginResult.token)
            setIsLoggedIn(true)
        }
    }


    return <Container fluid={"md"} className={"mt-3"}>
        <h1>SISTEMA DE ANÁLISE DE TRANSAÇÕES FINANCEIRAS</h1>
        <Form>

            <FormLabel className={"mb-0"}>EMAIL</FormLabel>
            <FormControl value={email} onChange={(e) => {setEmail(e.target.value)}}/>

            <FormLabel className={"mb-0 mt-3"}>PASSWORD</FormLabel>
            <FormControl value={password} onChange={(e) => {setPassword(e.target.value)}}/>

            <Button className={"mt-3"} onClick={handleLogin}>Login</Button>
        </Form>
    </Container>
}

export default Login