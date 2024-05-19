import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import FormInput from '../FormInput';

describe('FormInput component', () => {
    it('renders correctly with required props', () => {
        render(<FormInput label="Test Label" />);
        expect(screen.getByText('Test Label')).toBeInTheDocument();
        expect(screen.getByRole('textbox')).toBeInTheDocument();
    });

    it('displays the correct value', () => {
        render(<FormInput label="Test Label" value="Initial Value" />);
        const inputElement = screen.getByRole('textbox');
        expect(inputElement).toHaveValue('Initial Value');
    });

    it('calls onChange when value changes', () => {
        const handleChange = jest.fn();
        render(<FormInput label="Test Label" onChange={handleChange} />);
        const inputElement = screen.getByRole('textbox');
        fireEvent.change(inputElement, { target: { value: 'New Value' } });
        expect(handleChange).toHaveBeenCalledWith('New Value');
    });

    it('renders with defaultValue when value is not provided', () => {
        render(<FormInput label="Test Label" defaultValue="Default Value" />);
        const inputElement = screen.getByRole('textbox');
        expect(inputElement).toHaveValue('Default Value');
    });

    it('displays the icon when provided', () => {
        const TestIcon = () => <span data-testid="test-icon">Icon</span>;
        render(<FormInput label="Test Label" icon={<TestIcon />} />);
        expect(screen.getByTestId('test-icon')).toBeInTheDocument();
    });

    it('applies the background class correctly', () => {
        render(<FormInput label="Test Label" bg="bg-red-500" />);
        const containerElement = screen.getByRole('textbox').parentElement;
        expect(containerElement).toHaveClass('bg-red-500');
    });
});
