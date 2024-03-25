type InputErrorProps = {
    text?: string;
};

const InputError = ({ text }: InputErrorProps) => {
    return (
        <div className="text-xs text-red-500 font-semibold absolute -bottom-5 ml-5">
            {text}
        </div>
    );
};

export default InputError;
