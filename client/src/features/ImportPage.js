import {Button, Container, Form, FormControl, FormText, Table} from "react-bootstrap";
import {useEffect, useState} from "react";
import agent from "../api/agent";
import {useNavigate} from "react-router";
import MyTable from "../widgets/MyTable";

const ImportPage = () => {
    const [imports, setImports] = useState([])
    const [lastImport, setLastImport] = useState('')
    const {Imports}=agent

    useEffect(() => {
        const getImports=async()=>{
            const importsFromServer=await Imports.get()
            if(importsFromServer){
                setImports(importsFromServer)
            }
        }
        getImports()
    }, [lastImport])

    return <Container fluid={"md"}>
        <ImportForm setLastImport={setLastImport}/>
        <hr/>
        <TableImports imports={imports}/>
    </Container>
}

const TableImports = ({imports}) => {

    const navigate=useNavigate()

    const handleDetail=(importTransaction)=>{
        navigate(`/transactions/${importTransaction.id}`)
    }

    const headers=[
        ["DATA TRANSAÇÕES","DATA IMPORTAÇÃO","OPÇÕES"]
    ]

    const data=imports.map((importTransaction)=>{
        return [importTransaction.transactionsDate,importTransaction.importDate,
            <Button variant={"primary"} onClick={()=>handleDetail(importTransaction)}>Detalhar</Button>
        ]
    })

    return <>
        <MyTable title={"IMPORTAÇÕES REALIZADAS"} body={data} headers={headers} />
    </>
}

const ImportForm = ({setLastImport}) => {

    const [file, setFile] = useState()
    const {Transactions} = agent

    const handleUploadFile = async () => {

        const formData = new FormData()
        formData.append("file", file)
        const resultUpload = await Transactions.add(formData)
        if (resultUpload) {
            setLastImport([resultUpload])
        }
    }


    return <Container className={"mt-3"}>

        <Form>
            <h1 className={""}>IMPORTAR TRANSAÇÕES</h1>
            <FormControl type={"file"} onChange={event => setFile(event.target.files[0])}/>
            <FormText>Selecione o arquivo para realizar o upload</FormText>
            <div/>
            <Button className={"mt-3"} onClick={handleUploadFile}>Importar</Button>
        </Form>

    </Container>
}

export default ImportPage