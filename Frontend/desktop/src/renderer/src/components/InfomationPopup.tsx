import { FaRegCheckCircle } from 'react-icons/fa';
import { GoInfo } from 'react-icons/go';
import { MdErrorOutline } from 'react-icons/md';
import { cn } from '@renderer/utils/util';
import { ResultPopupType } from '@renderer/types/common';
import Button from './Button';

type InformationPopupProps = {
    title: string;
    body: string;
    popupType: ResultPopupType;
    closeAction: () => void;
};

const Icon = {
    Error: <MdErrorOutline className="w-[36px] h-[36px] text-red-500" />,
    Success: (
        <FaRegCheckCircle className="w-[36px] h-[36px] text-emerald-500" />
    ),
    Info: <GoInfo className="w-[36px] h-[36px] text-sky-800" />
};

const TitleColor = {
    Error: 'text-red-500',
    Success: 'text-emerald-500',
    Info: 'text-sky-800'
};

const InformationPopup = ({
    title,
    body,
    closeAction,
    popupType
}: InformationPopupProps) => {
    return (
        <div className="fixed inset-0 bg-black/25 bg-blur z-20 flex items-center justify-center">
            <div className="flex flex-col items-center px-5 py-5 w-[400px] bg-gray-50 border border-gray-200 rounded-lg">
                <div className="flex gap-2">
                    {Icon[popupType]}
                    <h1
                        className={cn(
                            'text-2xl font-semibold mb-4',
                            TitleColor[popupType]
                        )}
                    >
                        {title}
                    </h1>
                </div>
                <p className="text-base font-semibold mb-5 text-wrap">{body}</p>
                <Button
                    className={cn(
                        'px-2 py-1 border rounded-md text-base font-semibold w-fit text-white',
                        popupType === ResultPopupType.Success
                            ? 'bg-emerald-500'
                            : 'bg-red-500'
                    )}
                    text="Đóng"
                    action={closeAction}
                />
            </div>
        </div>
    );
};

export default InformationPopup;
