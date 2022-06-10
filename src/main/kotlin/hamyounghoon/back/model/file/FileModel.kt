package hamyounghoon.back.model.file

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class FileModel(
    @field:Schema(description = "파일명") var fileName: String = "",
    @field:Schema(description = "파일주소") var fileDownloadUrl: String = "",
    @field:Schema(description = "파일형식") var fileType: String? = null,
    @field:Schema(description = "파일크기") var fileSize: Long = 0
) {
    companion object {

        fun findContentType(fileName: String): String {
            return when (fileName.substring(fileName.indexOfLast { it == '.' } + 1).lowercase(Locale.getDefault())) {
                "aac" ->    ContentsType.type_aac
                "abw" ->    ContentsType.type_abw
                "arc" ->    ContentsType.type_arc
                "avi" ->    ContentsType.type_avi
                "azw" ->    ContentsType.type_azw
                "bin" ->    ContentsType.type_bin
                "bz" ->     ContentsType.type_bz
                "bz2" ->    ContentsType.type_bz2
                "csh" ->    ContentsType.type_csh
                "css" ->    ContentsType.type_css
                "csv" ->    ContentsType.type_csv
                "doc" ->    ContentsType.type_doc
                "epub" ->   ContentsType.type_epub
                "gif" ->    ContentsType.type_gif
                "htm" ->    ContentsType.type_htm
                "html" ->   ContentsType.type_html
                "ico" ->    ContentsType.type_ico
                "ics" ->    ContentsType.type_ics
                "jar" ->    ContentsType.type_jar
                "jpeg" ->   ContentsType.type_jpeg
                "jpg" ->    ContentsType.type_jpg
                "js" ->     ContentsType.type_js
                "json" ->   ContentsType.type_json
                "mid" ->    ContentsType.type_mid
                "midi" ->   ContentsType.type_midi
                "mpeg" ->   ContentsType.type_mpeg
                "mpkg" ->   ContentsType.type_mpkg
                "odp" ->    ContentsType.type_odp
                "ods" ->    ContentsType.type_ods
                "odt" ->    ContentsType.type_odt
                "oga" ->    ContentsType.type_oga
                "ogv" ->    ContentsType.type_ogv
                "ogx" ->    ContentsType.type_ogx
                "pdf" ->    ContentsType.type_pdf
                "ppt" ->    ContentsType.type_ppt
                "rar" ->    ContentsType.type_rar
                "rtf" ->    ContentsType.type_rtf
                "sh" ->     ContentsType.type_sh
                "svg" ->    ContentsType.type_svg
                "swf" ->    ContentsType.type_swf
                "tar" ->    ContentsType.type_tar
                "tif" ->    ContentsType.type_tif
                "tiff" ->   ContentsType.type_tiff
                "ttf" ->    ContentsType.type_ttf
                "txt" ->    ContentsType.type_txt
                "vsd" ->    ContentsType.type_vsd
                "wav" ->    ContentsType.type_wav
                "weba" ->   ContentsType.type_weba
                "webm" ->   ContentsType.type_webm
                "webp" ->   ContentsType.type_webp
                "woff" ->   ContentsType.type_woff
                "xhtml" ->  ContentsType.type_xhtml
                "xls" ->    ContentsType.type_xls
                "xlsx" ->   ContentsType.type_xlsx
                "xlsm" ->   ContentsType.type_xlsm
                "xml" ->    ContentsType.type_xml
                "xul" ->    ContentsType.type_xul
                "zip" ->    ContentsType.type_zip
                "3gp" ->    ContentsType.type_3gp
                "3g2" ->    ContentsType.type_3g2
                "7z" ->     ContentsType.type_7z
                else ->     "application/octet-stream"
            }
        }

        fun findExtensionType(contentType: String): String {
            return when (contentType.substring(contentType.indexOfLast { it == '.' } + 1).lowercase(Locale.getDefault())) {
                ContentsType.type_aac -> "aac"
                ContentsType.type_abw -> "abw"
                ContentsType.type_arc -> "arc"
                ContentsType.type_avi -> "avi"
                ContentsType.type_azw -> "azw"
                ContentsType.type_bin -> "bin"
                ContentsType.type_bz -> "bz"
                ContentsType.type_bz2 -> "bz2"
                ContentsType.type_csh -> "csh"
                ContentsType.type_css -> "css"
                ContentsType.type_csv -> "csv"
                ContentsType.type_doc -> "doc"
                ContentsType.type_epub -> "epub"
                ContentsType.type_gif -> "gif"
                ContentsType.type_htm -> "htm"
                ContentsType.type_html -> "html"
                ContentsType.type_ico -> "ico"
                ContentsType.type_ics -> "ics"
                ContentsType.type_jar -> "jar"
                ContentsType.type_jpeg -> "jpeg"
                ContentsType.type_jpg -> "jpg"
                ContentsType.type_js -> "js"
                ContentsType.type_json -> "json"
                ContentsType.type_mid -> "mid"
                ContentsType.type_midi -> "midi"
                ContentsType.type_mpeg -> "mpeg"
                ContentsType.type_mpkg -> "mpkg"
                ContentsType.type_odp -> "odp"
                ContentsType.type_ods -> "ods"
                ContentsType.type_odt -> "odt"
                ContentsType.type_oga -> "oga"
                ContentsType.type_ogv -> "ogv"
                ContentsType.type_ogx -> "ogx"
                ContentsType.type_pdf -> "pdf"
                ContentsType.type_ppt -> "ppt"
                ContentsType.type_rar -> "rar"
                ContentsType.type_rtf -> "rtf"
                ContentsType.type_sh -> "sh"
                ContentsType.type_svg -> "svg"
                ContentsType.type_swf -> "swf"
                ContentsType.type_tar -> "tar"
                ContentsType.type_tif -> "tif"
                ContentsType.type_tiff -> "tiff"
                ContentsType.type_ttf -> "ttf"
                ContentsType.type_txt -> "txt"
                ContentsType.type_vsd -> "vsd"
                ContentsType.type_wav -> "wav"
                ContentsType.type_weba -> "weba"
                ContentsType.type_webm -> "webm"
                ContentsType.type_webp -> "webp"
                ContentsType.type_woff -> "woff"
                ContentsType.type_xhtml -> "xhtml"
                ContentsType.type_xls -> "xls"
                ContentsType.type_xlsx -> "xlsx"
                ContentsType.type_xlsm -> "xlsx"
                ContentsType.type_xml -> "xml"
                ContentsType.type_xul -> "xul"
                ContentsType.type_zip -> "zip"
                ContentsType.type_3gp -> "3gp"
                ContentsType.type_3g2 -> "3g2"
                ContentsType.type_7z -> "7z"
                else ->     "txt"
            }
        }
    }
}
