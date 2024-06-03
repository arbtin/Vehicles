import { useEffect, useState} from 'react';
import './App.css';
import { deleteAircraft, getAllAircraft } from './api/AircraftApi.ts';
import { Aircraft} from './Aircraft.ts'
import AircraftList from './components/AircraftList.tsx'
import SearchBar from './components/SearchBar.tsx'
import { BrowserRouter as Router } from 'react-router-dom'
import { Route, Routes } from 'react-router-dom'
import NewAircraft from './components/NewAircraft.tsx'

function App() {
    const [rows, setRows] = useState<Aircraft[]>([]);

    useEffect(() => {
        document.title = "Aircraft";
        getAircraft();
    }, [])

    const getAircrafts = async () => {
        const response = await getAllAircraft();
        setRows(response);
    }

    return (
        <>
        <Router>
        <SearchBar />
        <Routes>
            <Route path='/' element={<AircraftList rowns={rows} />} />
            <Route path='/AircraftForm' element={<AircraftForm/>} />
            </Routes>
        </Router>
        </>
    );
}

export default App