import {useEffect, useState} from "react";
import agent from "../api/agent";
import MyTable from "../widgets/MyTable";
import {Button} from "react-bootstrap";

const Users=()=>{

    const {Users}=agent
    const [users,setUsers]=useState([])

    useEffect(()=>{

        const getUsers=async ()=>{
            const usersFromServer=await Users.get()
            if(usersFromServer){
                setUsers(usersFromServer)
            }
        }

        getUsers()

    },[Users])

    const handleRemove=async(user)=>{
        const id=user.id
        const userDeleteResponse=await Users.delete(id)
        if(userDeleteResponse===''){
            setUsers(users.filter(user=>user.id!== id))
        }
    }

    const headers=[
        ["NOME","EMAIL","OPÇÕES"]
    ]

    const data=users.map((user)=>{
        return [
            user.name,user.email, <Button variant={"primary"} onClick={()=>handleRemove(user)}>Excluir</Button>
        ]
    })

    return <>
        <MyTable title={"UTILIZADORES"} headers={headers} body={data} />
    </>
}

export default Users