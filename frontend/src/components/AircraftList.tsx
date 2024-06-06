import Aircraft from '../types/Aircraft.tsx'

function ListComponent({ rows }) {
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
                {rows.map((row: Aircraft) => (
                    <>
                        <td key={row.id} scope={'row'}>{row.id}</td>
                        <td>{row.airframe}</td>
                        <td>{row.pilot}</td>
                        <td>delete</td>
                    </>
                ))}
                </tbody>
            </table>

        </>
    )
}

export default ListComponent