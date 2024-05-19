import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import TextAreaInput from '../TextAreaInput';

describe('TextAreaInput component', () => {
    it('renders the label and icon correctly', () => {
        const label = 'Test Label';
        const icon = <span data-testid="test-icon">Icon</span>;

        render(<TextAreaInput label={label} icon={icon} />);

        expect(screen.getByText(label)).toBeInTheDocument();
        expect(screen.getByTestId('test-icon')).toBeInTheDocument();
    });

    it('renders with the correct background color', () => {
        const bg = 'bg-red-500';

        const { container } = render(<TextAreaInput label="Label" bg={bg} />);

        const wrapperDiv = container.querySelector('div');
        expect(wrapperDiv).toHaveClass(bg);
    });

    it('calls onChange when text is entered', () => {
        const handleChange = jest.fn();

        render(<TextAreaInput label="Label" onChange={handleChange} />);

        const textarea = screen.getByRole('textbox');
        fireEvent.change(textarea, { target: { value: 'New text' } });

        expect(handleChange).toHaveBeenCalledTimes(1);
    });

    it('displays the correct initial value', () => {
        const initialValue = 'Initial value';

        render(<TextAreaInput label="Label" value={initialValue} />);

        const textarea = screen.getByRole('textbox');
        expect(textarea).toHaveValue(initialValue);
    });
});
