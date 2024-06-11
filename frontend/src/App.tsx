import { useEffect, useState } from 'react'
import './App.css'
import { BrowserRouter as Router } from 'react-router-dom'
import { Route, Routes } from 'react-router-dom'
import { Aircraft } from './types/Aircraft.ts'
import SearchBar from './components/SearchBar.tsx'
import NewAircraft from './components/NewAircraft.tsx'
import AircraftList from './components/AircraftList.tsx'
import { getAllAircraft } from './api/AircraftApi.ts'

function App() {
    const [rows, setRows] = useState<Aircraft[]>([])

    useEffect(() => {
        getAllAircraft()
            .then(res => setRows(res))
            .catch((e) => console.error(e))
    }, [])

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