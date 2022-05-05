import {Table} from "react-bootstrap";

const MyTable=({headers, body,title}) => {


    return <>

        {
            title && <h1 className={"App mt-3"}>{title}</h1>
        }
        <Table striped bordered hover >
            <thead>
            {headers.map((header,i)=>{
                return <tr key={i}>
                    {
                        header.map((cell) => {
                            const key=Math.random()
                            return <th key={key} rowSpan={cell.rowSpan ?? 1} colSpan={cell.colSpan ?? 1} >{cell.label ?? cell}</th>
                        })
                    }
                </tr>
            })}


            </thead>
            <tbody>
            {
                body.map((row,i) => {
                    return <tr key={i}>
                        {
                            row.map(cell => {
                                const key=Math.random()
                                return <td key={key}>{cell}</td>
                            })
                        }
                    </tr>
                })
            }
            </tbody>
        </Table>
    </>
}


export default MyTable