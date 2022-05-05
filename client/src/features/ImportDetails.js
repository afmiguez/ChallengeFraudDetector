import {useParams} from "react-router";
import {useEffect, useState} from "react";
import agent from "../api/agent";
import {Col, Container, Form, FormControl, FormGroup, FormLabel, Row} from "react-bootstrap";
import MyTable from "../widgets/MyTable";


const ImportDetails=()=>{

    const {id}=useParams()
    const [importTransaction,setImportTransaction]=useState()
    const {Imports}=agent

    useEffect(()=>{

        const getImportById=async()=>{
            const importResult=await Imports.getById(id)
            if(importResult){
                setImportTransaction(importResult)
            }
        }

        getImportById()
    },[Imports])

    return <>
        <Container fluid={"md"}>

            <ImportFormDetails importTransaction={importTransaction } />

            <ImportTransactionDetails transactions={importTransaction?.transactions ?? []} />
        </Container>
    </>
}

const ImportTransactionDetails=({transactions})=>{
    const headers=[
        [{label:"Origem",colSpan:3},{label:"Destino",colSpan:3},{label:"Valor",rowSpan:2}],
        [{label: "Banco"},{label: "Agência"},{label: "Conta"},{label: "Banco"},{label: "Agência"},{label: "Conta"}]
    ]

    const data=transactions.map(row =>{
        return [
            row.sourceBankName,row.sourceBranchCode,row.sourceAccountCode,
            row.destinationBankName,row.destinationBranchCode,row.destinationAccountCode,
            row.amount
        ]
    })

    return <MyTable headers={headers} body={data}  title={"TRANSAÇÕES IMPORTADAS"} />
}

const ImportFormDetails=({importTransaction})=>{
    return <>
        <h1 className={"App"}>DETALHES DA IMPORTAÇÃO</h1>
        <Form style={{borderColor:"lightgray", borderStyle:"solid", borderWidth:"1px"}} className={"p-3"} >
            <MyFormRow value={importTransaction?.importDate}  label={"Importado em"}/>
            <MyFormRow value={importTransaction?.transactionsDate}  label={"Data transações"}/>
            <MyFormRow value={importTransaction?.user}  label={"Importado por"}/>
        </Form>
    </>
}

const MyFormRow=({value,label})=>{
    return <>

        <FormGroup as={Row}>
            <FormLabel column xs="2" className={"mb-0 p-0"}>{label}</FormLabel>
            <Col xs="3" className={"p-0"}>
                <FormControl disabled={true} value={value ?? ''} />
            </Col>
        </FormGroup>

    </>
}

export default ImportDetails