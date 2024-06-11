import { useEffect, useState } from 'react'
import './App.css'
//import { deleteAircraft, getAllAircraft } from './api/AircraftApi.ts'
import { Aircraft } from '../types/Aircraft.ts'
import AircraftList from './components/AircraftList.tsx'
import SearchBar from './components/SearchBar.tsx'
import { BrowserRouter as Router } from 'react-router-dom'
import { Route, Routes } from 'react-router-dom'
import NewAircraft from './components/NewAircraft.tsx'

function App() {
    const [rows, setRows] = useState<Aircraft[]>([])

    useEffect(() => {
        document.title = 'Aircraft'
        getAllAircraft()
    }, [])

    const getAllAircraft = async () => {
        const response = await getAllAircraft()
        setRows(response)
    }

    return (
        <>
            <div className="relative overflow-x-auto">
                <div className="container mx-auto">
                    <Router>
                        <SearchBar />
                        <Routes>
                            <Route path="/" element={<AircraftList rows={rows} />} />
                            <Route path="/AircraftForm" element={<NewAircraft />} />
                        </Routes>
                    </Router>
                </div>
            </div>
        </>
    )
}

export default App