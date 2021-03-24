object Anzeige: TAnzeige
  Left = 230
  Top = 429
  BorderIcons = [biSystemMenu]
  BorderStyle = bsDialog
  Caption = 'Berechnung'
  ClientHeight = 143
  ClientWidth = 312
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  FormStyle = fsStayOnTop
  OldCreateOrder = False
  OnClose = FormClose
  PixelsPerInch = 96
  TextHeight = 13
  object FortschrittLabel: TLabel
    Left = 208
    Top = 79
    Width = 29
    Height = 13
    Alignment = taRightJustify
    Caption = '100 %'
  end
  object ArtLabel: TLabel
    Left = 32
    Top = 13
    Width = 60
    Height = 13
    Alignment = taCenter
    Caption = 'Rechenart'
    Color = clBtnFace
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clWindowText
    Font.Height = -11
    Font.Name = 'MS Sans Serif'
    Font.Style = [fsBold]
    ParentColor = False
    ParentFont = False
  end
  object StatusLabel: TLabel
    Left = 32
    Top = 37
    Width = 247
    Height = 13
    Alignment = taCenter
    Caption = '10000000 von 10000000000000000 Berechnungen'
    Color = clBtnFace
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clWhite
    Font.Height = -11
    Font.Name = 'MS Sans Serif'
    Font.Style = []
    ParentColor = False
    ParentFont = False
  end
  object AbbruchButton: TButton
    Left = 112
    Top = 104
    Width = 100
    Height = 25
    Cancel = True
    Caption = 'Abbrechen'
    TabOrder = 0
    OnClick = AbbruchButtonClick
  end
  object Fortschritt: TProgressBar
    Left = 30
    Top = 60
    Width = 250
    Height = 17
    Min = 0
    Max = 100
    TabOrder = 1
  end
end
