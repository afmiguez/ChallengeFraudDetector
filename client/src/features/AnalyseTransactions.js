import {Button, Container, FormControl, FormText} from "react-bootstrap";
import {useState} from "react";
import agent from "../api/agent";
import MyTable from "../widgets/MyTable";

const SuspiciousTransactions = ({transactions}) => {

    const headers = [
        [{label: "Origem", colSpan: 3}, {label: "Destino", colSpan: 3}, {label: "Valor", rowSpan: 2}],
        [{label: "Banco"}, {label: "Agência"}, {label: "Conta"}, {label: "Banco"}, {label: "Agência"}, {label: "Conta"}]
    ]

    const data = transactions.map(row => {
        return [
            row.sourceBankName, row.sourceBranchCode, row.sourceAccountCode,
            row.destinationBankName, row.destinationBranchCode, row.destinationAccountCode,
            row.amount
        ]
    })

    return <>
        <MyTable headers={headers} body={data} title={"TRANSAÇÕES SUSPEITAS"}/>
    </>
}

const SuspiciousAccounts = ({accounts}) => {
    const headers = [["BANCO", "AGÊNCIA", "CONTA", "VALOR MOVIMENTADO", "TIPO MOVIMENTAÇÃO"]]
    const data = accounts.map(row => {
        return [row.bankName, row.branchCode, row.accountCode, row.amount, row.typeMovimentation]
    })
    return <>
        <MyTable headers={headers} body={data} title={"AGÊNCIAS SUSPEITAS"}/>
    </>
}

const SuspiciousBranches = ({branches}) => {
    const headers = [["BANCO", "AGÊNCIA", "VALOR MOVIMENTADO", "TIPO MOVIMENTAÇÃO"]]
    const data = branches.map(row => {
        return [row.bankName, row.branchCode, row.amount, row.typeMovimentation]
    })
    return <>
        <MyTable headers={headers} body={data} title={"AGÊNCIAS SUSPEITAS"}/>
    </>
}

const AnalyseTransactions = () => {
    const [suspiciousTransactions, setSuspiciousTransactions] = useState()
    return <>
        <Container fluid={"sm"}>

            <h1>ANÁLISE DE TRANSAÇÕES SUSPEITAS</h1>
            <AnalyseInput setSuspiciousTransactions={setSuspiciousTransactions}/>
            {
                suspiciousTransactions?.transactions.length>0 ?
                <SuspiciousTransactions transactions={suspiciousTransactions?.transactions ?? []}/>
                    : <h1>Sem transações suspeitas</h1>

            }
            {
                suspiciousTransactions?.accounts.length>0 ?
                <SuspiciousAccounts accounts={suspiciousTransactions?.accounts ?? []}/>
                    : <h1>Sem contas suspeitas</h1>
            }
            {
                suspiciousTransactions?.branches.length >0 ?
                <SuspiciousBranches branches={suspiciousTransactions?.branches ?? []}/>
                    : <h1>Sem agências suspeitas</h1>
            }


        </Container>
    </>
}

const AnalyseInput = ({setSuspiciousTransactions}) => {

    const {Transactions} = agent
    const handleAnalyse = async () => {
        const suspiciousTransactions = await Transactions.getSuspicious(month)
        if (suspiciousTransactions) {
            setSuspiciousTransactions(suspiciousTransactions)
        }
    }
    const [month, setMonth] = useState('')
    return <>
        <FormText>Selecione o mês para analisar as transações</FormText>
        <FormControl type={"month"} value={month} onChange={(e) => setMonth(e.target.value)}/>
        <Button onClick={handleAnalyse}> Realizar análise</Button>
    </>
}

export default AnalyseTransactions