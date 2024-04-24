import warehouseIconSrc from '@renderer/assets/warehouse-icon.png';

type FormTitleProps = {
    title: string;
};

const FormTitle = ({ title }: FormTitleProps) => {
    return (
        <div className="flex items-center gap-2">
            <img alt="form-icon" src={warehouseIconSrc} />
            <h1 className="text-xl font-semibold">{title}</h1>
        </div>
    );
};

export default FormTitle;
