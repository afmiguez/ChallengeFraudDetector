import {Nav, Navbar} from "react-bootstrap";
import {Route, Routes} from "react-router";
import {Link} from "react-router-dom";
import ImportPage from "./ImportPage";
import ImportDetails from "./ImportDetails";
import AnalyseTransactions from "./AnalyseTransactions";
import Users from "./Users";

const MyNav=({setIsLoggedIn})=>{

    const handleLogout=()=>{
        sessionStorage.removeItem("token")
        setIsLoggedIn(false)
    }

    return <>
        <Navbar className={"navbar navbar-expand-lg navbar-dark bg-dark"} >
            <Nav>
                <Nav.Item>
                    <Nav.Link as={Link} to={"/"} >IMPORTAÇÕES</Nav.Link>
                </Nav.Item>
            </Nav>
            <Nav>
                <Nav.Item>
                    <Nav.Link as={Link} to={"/users"} >USUÁRIOS</Nav.Link>
                </Nav.Item>
            </Nav>
            <Nav>
                <Nav.Item>
                    <Nav.Link as={Link} to={"/analyse"} >ANALISAR</Nav.Link>
                </Nav.Item>
            </Nav>
            <Nav>
                <Nav.Item>
                    <Nav.Link onClick={handleLogout} >SAIR</Nav.Link>
                </Nav.Item>
            </Nav>
        </Navbar>


        <Routes>
            <Route path={"/"} element={<ImportPage />} />
            <Route path={"/users"} element={<Users />} />
            <Route path={"/transactions/:id"} element={<ImportDetails />} />
            <Route path={"/analyse"} element={<AnalyseTransactions />} />
        </Routes>

    </>
}

export default MyNav