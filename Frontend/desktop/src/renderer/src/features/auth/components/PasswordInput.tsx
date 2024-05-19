import { ReactNode, useState } from 'react';
import { BiSolidShow, BiSolidHide } from 'react-icons/bi';

type PasswordInputProps = {
    placeHolder: string;
    value: string;
    onChange: any;
    label: string;
    name: string;
    errorMessage?: ReactNode;
};

const PasswordInput = ({
    label,
    value,
    onChange,
    name,
    placeHolder,
    errorMessage
}: PasswordInputProps) => {
    const [isShowed, setIsShowed] = useState(false);
    const showPassword = () => setIsShowed(true);
    const hidePassword = () => setIsShowed(false);

    return (
        <div className="flex flex-col relative">
            <label
                htmlFor="password"
                className="font-semibold text-base text-sky-800"
            >
                {placeHolder}
            </label>
            {errorMessage}
            <div className="flex items-center justify-between border border-sky-800 text-sky-800 bg-white font-semibold rounded-md text-sm px-4 py-2">
                <input
                    className="outline-none flex-1"
                    id={label}
                    value={value}
                    onChange={onChange}
                    type={isShowed ? 'text' : 'password'}
                    placeholder={placeHolder}
                    name={name}
                />
                {!isShowed ? (
                    <BiSolidShow
                        className="cursor-pointer"
                        onClick={showPassword}
                    />
                ) : (
                    <BiSolidHide
                        className="cursor-pointer"
                        onClick={hidePassword}
                    />
                )}
            </div>
        </div>
    );
};

export default PasswordInput;
