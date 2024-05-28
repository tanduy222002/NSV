import { cn } from '@renderer/utils/util';
import { useRef } from 'react';
import { GoMoveToEnd } from 'react-icons/go';
import { IoClose } from 'react-icons/io5';

type SlotRowProps = {
    i: number;
    id: number;
    product: string;
    quality: string;
    packaged: string;
    inSlotWeight: number;
    location: string;
    takenWeight: number;
    takenArea: number;
    loadToExportTicket: (param: {
        id: number;
        weight: number;
        area: number;
    }) => void;
    unloadFromExportTicket: (param: { id: number }) => void;
};

const SlotRow = ({
    i,
    id,
    product,
    quality,
    packaged,
    inSlotWeight,
    location,
    takenWeight,
    takenArea,
    loadToExportTicket,
    unloadFromExportTicket
}: SlotRowProps) => {
    const weightRef = useRef<HTMLInputElement>(null);
    const areaRef = useRef<HTMLInputElement>(null);

    return (
        <tr key={id} className="border border-1">
            <td className="text-sm font-semibold px-2 py-2">{id}</td>
            <td className="text-sm font-semibold px-2">{product}</td>
            <td className="text-sm font-semibold px-2">{quality}</td>
            <td className="text-sm font-semibold px-2">{packaged}</td>
            <td className="text-sm font-semibold px-2">{inSlotWeight} kg</td>
            <td className="text-sm font-semibold px-2 w-fit">{location}</td>
            <td
                className={cn(
                    'text-sm font-semibold px-2 w-fit',
                    takenArea && takenWeight ? 'text-sky-800' : 'text-gray-300'
                )}
            >
                {`${takenArea} m2, ${takenWeight} kg`}
            </td>
            <td className="w-fit px-2">
                <input
                    className="outline-none px-3 text-sm  text-black w-[70px] focus:outline-sky-800 rounded-sm"
                    placeholder="... kg"
                    ref={weightRef}
                />
            </td>
            <td className="w-fit px-2">
                <input
                    className="outline-none px-3 text-sm text-black w-[70px] focus:outline-sky-800 rounded-sm"
                    placeholder="... m2"
                    ref={areaRef}
                />
            </td>
            <td className="w-fit">
                <div className="flex items-center justify-center">
                    <input type="checkbox" />
                </div>
            </td>
            <td className="w-fit">
                <div className="flex items-center gap-2 justify-center">
                    <GoMoveToEnd
                        onClick={() => {
                            loadToExportTicket({
                                id: i,
                                weight: Number(weightRef!.current!.value),
                                area: Number(areaRef!.current!.value)
                            });
                            weightRef!.current!.value = '';
                            areaRef!.current!.value = '';
                        }}
                        className="cursor-pointer text-sky-800 w-[24px] h-[24px] rounded-full hover:bg-sky-100 p-1"
                    />
                    <IoClose
                        onClick={() => unloadFromExportTicket({ id: i })}
                        className="cursor-pointer text-red-500 w-[24px] h-[24px] rounded-full hover:bg-red-100 p-1"
                    />
                </div>
            </td>
        </tr>
    );
};

export default SlotRow;
