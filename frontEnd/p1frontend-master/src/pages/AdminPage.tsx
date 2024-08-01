import React, { useEffect, useState } from 'react';
import config from '../config';
import { User as UserType } from '../interface/types';
import DataTable from 'react-data-table-component';
import '../css/AdminPage.css';
import { Link } from 'react-router-dom';
import { Button } from 'react-bootstrap';
import { useAuth } from '../context/AuthContext';

/* Admin can see all users and ban/ unban user or goto posts page throught "View Posts" button
        to change/ delete posts or comments there.
*/
const AdminPage = () => {
    const [users, setUsers] = useState<UserType[]>([]);
    const [data, setData] = useState(users);
    const [filterIndicator, setFilterIndicator] = useState(false);
    const [selectedRowUserId, setSelectedRowUserId] = useState(0);
    const [userActiveNow, setUserActiveNow] = useState("");
    const [spinner, setSpinner] = useState("");
    const { user, token } = useAuth();


    //Fetch users data //
    useEffect(() => {
        setSpinner("loading...");
        const fetchData = async () => {
            await fetch(`${config.BASE_URL}/api/users`, { credentials: 'include' })
                .then(response => response.json())
                .then(data => setUsers(data))
                .catch(error => { console.error('Fetch Users error:', error) })
        };
        fetchData();
        setSpinner("");
    }, [userActiveNow]);

    // Ban User//
    const BanUserUtility = async (selectedRowUserId: number) => {
        await fetch(`${config.BASE_URL}/api/users/${selectedRowUserId}/ban`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            credentials: 'include',
        }).then(response => response.json())
            .catch(error => { console.error('Ban error:', error) })

        setUserActiveNow("True");
        alert("Ban is Successful");
    }

    //Unban User//
    const UnbanUserUtility = async (selectedRowUserId: number) => {
        await fetch(`${config.BASE_URL}/api/users/${selectedRowUserId}/unban`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            credentials: 'include',
        }).then(response => response.json())
            .catch(error => { console.error('Unban error:', error) })

        setUserActiveNow("False");
        alert("Unban is Successful");
    }

    // List of column to display in dataset//
    const columns = [
        {
            name: "ID",
            //selector: (row: any) => row.userId,
            sortable: true,
            width: "60px",
            cell: (row: any) => <div data-tag="allowRowEvents" style={{ fontWeight: 'bold' }}> {row.userId}</div>
        },
        {
            name: "First Name",
            selector: (row: any) => row.firstName,
            sortable: true,
        },
        {
            name: "Last Name",
            selector: (row: any) => row.lastName,
            sortable: true,
        },
        {
            name: "Email",
            selector: (row: any) => row.email,
            sortable: false,
        },
        {
            name: "User Name",
            selector: (row: any) => row.username,
            sortable: false,
        },
        {
            name: "Active",
            cell: (row: any) => <div> {row.active ? "True" : "False"}</div>,
            sortable: false,
        },
        {
            name: "Bio",
            selector: (row: any) => row.bio,
            sortable: false,
            width:'325px',
        },
    ];

    //It gives header style in the dataset//
    const tableCustomStyles = {
        headRow: {
            style: {
                color: '#223336',
                backgroundColor: 'pink'
            },
        }
    }
   
    //Search by userID//
    const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newUsers = users.filter((row) => {
            return (
                row.userId
                    .toString()
                    .includes(e.target.value));
        });
        setFilterIndicator(true);
        setData(newUsers);
    };

    //Ban User on successful method "PUT"//
    const handleBanUser = () => { BanUserUtility(selectedRowUserId) }

    //Unban User//
    const handleUnbanUser = () => { UnbanUserUtility(selectedRowUserId) }

    //get userid selected row //
    const handleGetRowData = (row: any) => {setSelectedRowUserId(row.userId);}


    return (
        <>
            <div className="container-fluid mt-3 bg-primary divMainContainer">
                <span> {spinner} </span>
                <table className="table  tableSize ">
                    <center><h3>Admin Management App</h3></center>
                    <DataTable
                        title="  "
                        columns={columns}
                        data={filterIndicator ? data : users}
                        fixedHeader
                        selectableRows
                        selectableRowsSingle={true}
                        onRowClicked={(row) => { handleGetRowData(row) }}
                        pagination
                        selectableRowsHighlight={true}
                        clearSelectedRows={true}
                        subHeader
                        subHeaderComponent={
                            <div className="d-flex p-3  optionsBar">
                                <Link to="/Home" className="p-1 mt-2 bg-success btnSize"> Home </Link>

                                <Button variant="success" className="p-1 mt-2 bg-warning btnSize"
                                    onClick={handleBanUser} >

                                    Ban User
                                </Button>
                                <Button variant="success" className="p-1 mt-2 bg-warning btnSize"
                                    onClick={handleUnbanUser} >
                                    Unban User
                                </Button>

                                <Link to={`/profile/${selectedRowUserId}`} className="p-1 mt-2 bg-info btnSize"> View Profile </Link>

                                <div className="divForSearch">
                                    <label> <b>Search By Id:</b> </label>
                                    <input
                                        type="search"
                                        className="form-control-sm border border-primary ps-3 "
                                        placeholder="Search By Id"
                                        onChange={handleSearch}
                                    />

                                </div>

                            </div>
                        }
                        responsive
                        customStyles={tableCustomStyles}
                    />

                </table>
            </div>
        </>
    );
};
export default AdminPage;
