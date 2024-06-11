import { UserIcon } from 'lucide-react'

const AircraftCard = (props: AircraftCardProps) => {
    const { airframe, id, pilot } = props
    return (
        <tr key={id}>
            <td scope={'row'}>{id}</td>
            <td>{airframe}</td>
            <td><UserIcon size={16} /> {pilot}</td>
            <td>delete</td>
        </tr>
    )
}

type AircraftCardProps = {
    airframe: string
    id: number
    pilot: string
}

export default AircraftCard