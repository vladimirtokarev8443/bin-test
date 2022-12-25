package com.example.binlisttest

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputFilter
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.binlisttest.databinding.FragmentBinInputBinding
import com.example.binlisttest.models.BinInfo
import com.example.binlisttest.ui.BinInputViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class BinInputFragment : Fragment(R.layout.fragment_bin_input) {

    private var _binding: FragmentBinInputBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BinInputViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBinInputBinding.bind(view)

        observeViewModel()

        binding.numberImputBinInput.editText?.filters = arrayOf(object : InputFilter {
            override fun filter(p0: CharSequence?, p1: Int, p2: Int, p3: Spanned?, p4: Int, p5: Int
            ): CharSequence {
                val str = binding.numberImputBinInput.editText?.text.toString()


                return if (str.length == 4) " "+p0 else p0.toString()
            }
        })

        binding.numberImputBinInput.setStartIconOnClickListener {
            viewModel.updateUiState(binding.numberImputBinInput.editText?.text.toString())
        }

    }

    private fun observeViewModel() {
        viewModel.uiStateFlow
            .onEach {
                binding.progressBar2.isIndeterminate = it.isLoading
                bindBinInfo(it.binInfo)
                initAutoCompleteText(it.binList)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun bindBinInfo(binInfo: BinInfo?) = with(binding) {
        nameBankBinInput.setTextAndColor(binInfo?.bank?.name)
        urlBankBinInput.text = binInfo?.bank?.url ?: getString(R.string.default_text)
        phoneBankBinInput.text = binInfo?.bank?.phone ?: getString(R.string.default_text)
        valueSchemeBinInput.setTextAndColor(binInfo?.scheme)
        valueBrandBinInput.setTextAndColor(binInfo?.brand)
        valueLengthBinInput.setTextAndColor(binInfo?.number?.length)
        emojiCountryBinInput.setTextAndColor(binInfo?.country?.emoji)
        nameCountryBinInput.setTextAndColor(binInfo?.country?.name)
        valueLuhnBinInput.isSpanText(binInfo?.number?.luhn)
        valuePrepaidBinInput.isSpanText(binInfo?.prepaid)
        valueTypeBinInput.typeSpanText(binInfo?.type)
        locationBinInput.locationSpanText(binInfo?.country?.latitude, binInfo?.country?.longitude)
    }

    @SuppressLint("ResourceAsColor")
    private fun TextView.setTextAndColor(value: Any?) {
        text = if (value != null) {
            setTextColor(resources.getColor(R.color.black, null))
            value.toString()
        } else {
            setTextColor(androidx.appcompat.R.color.secondary_text_default_material_light)
            getString(R.string.default_text)
        }
    }


    private fun TextView.isSpanText(value: Boolean?) {
        val span = SpannableString("YES / NO")
        if (value == null){
            text = span
            return
        }

        if (value) {
            span.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.black, null)),
                0,
                3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else {
            span.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.black, null)),
                5,
                8,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        text = span
    }

    private fun TextView.typeSpanText(value: String?) {
        val span = SpannableString("Debit / Credit")
        if (value == null){
            text = span
            return
        }
        if (value.equals("debit")) {
            span.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.black, null)),
                0,
                5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else {
            span.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.black, null)),
                7,
                14,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        text = span
    }

    private fun TextView.locationSpanText(lat: Int?, lon: Int?){
        if (lat == null){
            text = SpannableString(getString(R.string.default_location))
            return
        }
        val locat = getString(R.string.location, lat.toString(), lon.toString())
        val span = SpannableString(locat)
        span.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.black, null)),
            11,
            11+lat.toString().length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        span.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.black, null)),
            locat.length-lon.toString().length-1,
            locat.length-1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = span
    }

    private fun initAutoCompleteText(items: List<String>){
        val adapter = ArrayAdapter(requireContext(), R.layout.item_auto_text, items)
        (binding.numberImputBinInput.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}