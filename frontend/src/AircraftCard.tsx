import { UserIcon } from 'lucide-react'

const AircraftCard = (props: AircraftCardProps) => {
    const { airframe, id, pilot } = props
    return (
        <li className=" flex flex-col gap-1 border rounded-md border-gray-200 py-2 px-4">
            <span className="text-xl font-medium capitalize">{airframe}</span>
            <div className="flex items-center gap-1 text-zinc-600">
                <UserIcon size={16} />
                <span className="text-sm" key={id}>{pilot}</span>
            </div>
        </li>




    )
}

type AircraftCardProps = {
    airframe: string
    id: number
    pilot: string
    crew_chief_id: number
}

export default AircraftCard