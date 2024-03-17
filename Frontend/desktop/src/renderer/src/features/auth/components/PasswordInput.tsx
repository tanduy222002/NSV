import { useState } from 'react';
import { BiSolidShow, BiSolidHide } from 'react-icons/bi';

type PasswordInputProps = {
    placeHolder: string;
    label: string;
};

const PasswordInput = ({ label, placeHolder }: PasswordInputProps) => {
    const [isShowed, setIsShowed] = useState(false);
    const showPassword = () => setIsShowed(true);
    const hidePassword = () => setIsShowed(false);

    return (
        <div className="flex flex-col">
            <label
                htmlFor="password"
                className="font-semibold text-base text-[#1A3389]"
            >
                {placeHolder}
            </label>
            <div className="flex items-center justify-between border border-[#1A3389] text-[#1A3389] bg-white font-semibold rounded-md text-sm px-4 py-2">
                <input
                    className="outline-none flex-1"
                    id={label}
                    type={isShowed ? 'text' : 'password'}
                    placeholder={placeHolder}
                />
                {isShowed ? (
                    <BiSolidShow
                        className="cursor-pointer"
                        onClick={hidePassword}
                    />
                ) : (
                    <BiSolidHide
                        className="cursor-pointer"
                        onClick={showPassword}
                    />
                )}
            </div>
        </div>
    );
};

export default PasswordInput;
