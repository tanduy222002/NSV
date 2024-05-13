import { ReactNode } from 'react';

type TextInputProps = {
    type?: string;
    label: string;
    value: string;
    name: string;
    placeHolder: string;
    onChange?: any;
    errorMessage?: ReactNode;
};

const TextInput = ({
    type = 'text',
    label,
    value,
    name,
    placeHolder,
    onChange,
    errorMessage
}: TextInputProps) => {
    return (
        <div className="flex flex-col gap-1 relative">
            <label
                htmlFor={label}
                className="font-semibold text-base text-sky-800"
            >
                {placeHolder}
            </label>
            <input
                className="outline-none border border-sky-800 text-sky-800 font-semibold rounded-md text-sm px-4 py-2"
                type={type}
                placeholder={placeHolder}
                name={name}
                value={value}
                id={label}
                onChange={onChange}
            />
            {errorMessage}
        </div>
    );
};

export default TextInput;
