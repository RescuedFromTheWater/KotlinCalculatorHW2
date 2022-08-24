package com.example.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.calculator.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding: FragmentCalculatorBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCalculatorBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun setText(string: String, clear: Boolean) {
            if (clear) {
                binding.buttonResult.text = ""
                binding.buttonExpression.append(string)
            } else {
                binding.buttonExpression.append(binding.buttonResult.text)
                binding.buttonExpression.append(string)
                binding.buttonResult.text = ""
            }
        }

        binding.buttonZero.setOnClickListener { setText("0", true) }
        binding.buttonOne.setOnClickListener { setText("1", true) }
        binding.buttonTwo.setOnClickListener { setText("2", true) }
        binding.buttonThree.setOnClickListener { setText("3", true) }
        binding.buttonFour.setOnClickListener { setText("4", true) }
        binding.buttonFive.setOnClickListener { setText("5", true) }
        binding.buttonSix.setOnClickListener { setText("6", true) }
        binding.buttonSeven.setOnClickListener { setText("7", true) }
        binding.buttonEight.setOnClickListener { setText("8", true) }
        binding.buttonNine.setOnClickListener { setText("9", true) }

        binding.buttonOpen.setOnClickListener { setText("(", true) }
        binding.buttonClosed.setOnClickListener { setText(")", true) }
        binding.buttonDivision.setOnClickListener { setText("÷", true) }
        binding.buttonMultiplication.setOnClickListener { setText("x", true) }
        binding.buttonMinus.setOnClickListener { setText("-", true) }
        binding.buttonPlus.setOnClickListener { setText("+", true) }
        binding.buttonPoint.setOnClickListener { setText(".", true) }

        binding.buttonClear.setOnClickListener {
            binding.buttonExpression.text = ""
            binding.buttonResult.text = ""
        }

        binding.buttonBack.setOnClickListener {
            val numbers = binding.buttonExpression.text
            val expression = numbers.dropLast(1)
            binding.buttonExpression.text = expression

        }

        binding.buttonEqually.setOnClickListener {

            val text = binding.buttonExpression.text.toString()
            val math = Calculator()
            val resultAnswer = text.let { math.someLogic(it) }
            val finishResult = resultAnswer.toLong()

            try {
                if (resultAnswer == finishResult.toFloat()) {
                    binding.buttonResult.text = finishResult.toString()
                } else {
                    Toast.makeText(requireContext(), "Error!", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error!" + e.message, Toast.LENGTH_LONG).show()
            }
            binding.buttonResult.text = resultAnswer.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}