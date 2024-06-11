import { Aircraft } from '../types/Aircraft.tsx'
import AircraftCard from '../AircraftCard.tsx'

function ListComponent({ rows: rows }) {
    return (
        <>
            <h1>Aircraft</h1>
            <table className="table-auto w-full text-m">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Airframe</th>
                    <th scope="col">Pilot</th>
                </tr>
                </thead>
                <tbody>
                {rows?.map((row: Aircraft) => {
                    return (
                        <AircraftCard
                            key={row.id}
                            {...row}
                        />
                    )
                })}
                </tbody>
            </table>
        </>
    )
}

export default ListComponent