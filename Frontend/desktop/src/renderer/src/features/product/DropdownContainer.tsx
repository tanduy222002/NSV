import { ReactNode, useState } from 'react';
import { MdKeyboardArrowUp, MdKeyboardArrowDown } from 'react-icons/md';

type DropdownContainerProps = {
    title: string;
    children: ReactNode;
};

const DropdownContainer = ({ title, children }: DropdownContainerProps) => {
    const [open, setOpen] = useState(false);
    const toggleOpen = () => setOpen((prev) => !prev);
    return (
        <div>
            <div className="flex items-center gap-2">
                <h1 className="text-lg font-semibold cursor-pointer">
                    {title}
                </h1>
                {open ? (
                    <MdKeyboardArrowUp
                        className="w-[20px] h-[20px] hover:bg-slate-300 rounded-full hover:border border-slate-500"
                        onClick={toggleOpen}
                    />
                ) : (
                    <MdKeyboardArrowDown
                        className="w-[20px] h-[20px] hover:bg-slate-300 rounded-full hover:border border-slate-500"
                        onClick={toggleOpen}
                    />
                )}
            </div>
            {open && children}
        </div>
    );
};

export default DropdownContainer;
