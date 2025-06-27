import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.charset.StandardCharsets; // 이 줄을 추가합니다.

public class FileUrlGeneratorGUI extends JFrame {

    private JTextArea urlTextArea;
    private JFileChooser fileChooser;
    private JComboBox<String> favoriteFoldersComboBox; // 즐겨찾기 폴더 드롭다운
    private List<String> favoriteFolders; // 즐겨찾기 폴더 경로 저장 리스트
    private final String FAVORITES_FILE = "favorites.txt"; // 즐겨찾기 저장 파일

    public FileUrlGeneratorGUI() {
        setTitle("로컬 파일 URL 생성기 (by Gemini AI)");
        setSize(800, 600); // 창 크기 좀 더 확장
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 배치

        // Look and Feel을 시스템 기본값으로 설정하여 좀 더 현대적인 느낌 (Windows 기준)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // JFileChooser 초기화 및 설정
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setMultiSelectionEnabled(false); // 여러 파일 선택 비활성화 (현재 기능에 따라)

        // UI 컴포넌트 생성
        urlTextArea = new JTextArea();
        urlTextArea.setLineWrap(true); // 줄 바꿈 설정
        urlTextArea.setWrapStyleWord(true); // 단어 단위로 줄 바꿈
        urlTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // 폰트 설정
        urlTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백 추가
        JScrollPane scrollPane = new JScrollPane(urlTextArea); // 스크롤바 추가
        scrollPane.setBorder(BorderFactory.createTitledBorder("생성된 URL 목록")); // 제목 있는 테두리 추가

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 격자 레이아웃, 간격 추가
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton selectFileButton = new JButton("① 파일 선택");
        JButton selectFolderButton = new JButton("② 폴더 선택 (파일 목록)");
        JButton copyToClipboardButton = new JButton("③ URL 모두 복사");

        // 버튼 스타일 (선택 사항)
        styleButton(selectFileButton);
        styleButton(selectFolderButton);
        styleButton(copyToClipboardButton);

        buttonPanel.add(selectFileButton);
        buttonPanel.add(selectFolderButton);
        buttonPanel.add(copyToClipboardButton);

        // 즐겨찾기 패널
        JPanel favoritePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5)); // 플로우 레이아웃, 간격 추가
        favoritePanel.setBorder(BorderFactory.createTitledBorder("즐겨찾기 폴더 관리")); // 제목 있는 테두리

        favoriteFolders = loadFavoriteFolders(); // 즐겨찾기 불러오기
        favoriteFoldersComboBox = new JComboBox<>(favoriteFolders.toArray(new String[0]));
        favoriteFoldersComboBox.setPreferredSize(new Dimension(300, 28)); // 드롭다운 크기 조절

        JButton addFavoriteButton = new JButton("즐겨찾기 추가");
        JButton removeFavoriteButton = new JButton("즐겨찾기 제거");

        styleButton(addFavoriteButton);
        styleButton(removeFavoriteButton);

        favoritePanel.add(favoriteFoldersComboBox);
        favoritePanel.add(addFavoriteButton);
        favoritePanel.add(removeFavoriteButton);

        // 이벤트 리스너 설정
        selectFileButton.addActionListener(e -> selectFile());
        selectFolderButton.addActionListener(e -> selectFolder());
        copyToClipboardButton.addActionListener(e -> copyToClipboard());
        addFavoriteButton.addActionListener(e -> addFavoriteFolder());
        removeFavoriteButton.addActionListener(e -> removeFavoriteFolder());
        favoriteFoldersComboBox.addActionListener(e -> {
            if (favoriteFoldersComboBox.getSelectedItem() != null) {
                // 즐겨찾기 선택 시 해당 폴더의 파일 URL 생성
                File selectedFavFolder = new File((String) favoriteFoldersComboBox.getSelectedItem());
                if (selectedFavFolder.isDirectory()) {
                    generateUrlsFromFolder(selectedFavFolder);
                } else {
                    JOptionPane.showMessageDialog(this, "선택된 즐겨찾기 폴더가 유효하지 않거나 존재하지 않습니다.\n" +
                            "목록에서 제거하는 것을 권장합니다.", "경고", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // 상단 컨트롤 패널
        JPanel topControlPanel = new JPanel();
        topControlPanel.setLayout(new BorderLayout());
        topControlPanel.add(buttonPanel, BorderLayout.NORTH);
        topControlPanel.add(favoritePanel, BorderLayout.CENTER);

        // 메인 컨테이너에 컴포넌트 추가
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(topControlPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // 버튼 스타일링 헬퍼 메서드 (선택 사항)
    private void styleButton(JButton button) {
        button.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        button.setFocusPainted(false); // 포커스 테두리 제거
        button.setBackground(new Color(230, 230, 230)); // 배경색
        button.setForeground(Color.BLACK); // 글자색
        button.setBorder(BorderFactory.createRaisedBevelBorder()); // 입체감 있는 테두리
    }

    // 파일 선택 메서드
    private void selectFile() {
        fileChooser.setDialogTitle("단일 파일 선택");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setApproveButtonText("선택"); // 버튼 텍스트 변경

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileUrl = convertFilePathToFileUrl(selectedFile);
            urlTextArea.setText(fileUrl);
        }
    }

    // 폴더 선택 메서드
    private void selectFolder() {
        fileChooser.setDialogTitle("폴더 선택 (내부 파일 URL 생성)");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setApproveButtonText("선택"); // 버튼 텍스트 변경

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            generateUrlsFromFolder(selectedFolder);
        }
    }

    // 폴더 내 파일 URL 생성
    private void generateUrlsFromFolder(File folder) {
        if (!folder.isDirectory()) {
            JOptionPane.showMessageDialog(this, "유효한 폴더를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        // 스트림 API를 사용하여 깔끔하게 파일 목록 처리
        try (Stream<File> filesStream = Files.list(folder.toPath())
                .map(java.nio.file.Path::toFile)) {
            filesStream.filter(File::isFile) // 파일만 필터링
                    .forEach(file -> sb.append(convertFilePathToFileUrl(file)).append("\n"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "폴더 내용을 읽는 중 오류가 발생했습니다: " + e.getMessage(), "오류",
                    JOptionPane.ERROR_MESSAGE);
        }
        urlTextArea.setText(sb.toString().trim());
    }

    // 파일 경로를 file:// URL로 변환 (인코딩 없음)
    private String convertFilePathToFileUrl(File file) {
        String path = file.getAbsolutePath();
        path = path.replace("\\", "/"); // 백슬래시를 슬래시로 변경
        // Windows 드라이브 문자의 콜론(C:) 뒤에 슬래시 하나를 더 추가 (file:///C:/ 형태로 만들기 위해)
        if (path.length() > 2 && path.charAt(1) == ':' && path.charAt(2) != '/') {
            path = path.substring(0, 2) + "/" + path.substring(2);
        }
        return "file:///" + path; // file:/// 접두사 붙이기
    }

    // 텍스트 영역의 내용을 클립보드에 복사
    private void copyToClipboard() {
        StringSelection stringSelection = new StringSelection(urlTextArea.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        JOptionPane.showMessageDialog(this, "URL이 클립보드에 복사되었습니다!", "정보", JOptionPane.INFORMATION_MESSAGE);
    }

    // 즐겨찾기 폴더 추가
    private void addFavoriteFolder() {
        fileChooser.setDialogTitle("즐겨찾기에 추가할 폴더 선택");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setApproveButtonText("추가"); // 버튼 텍스트 변경

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            String folderPath = selectedFolder.getAbsolutePath();
            if (!favoriteFolders.contains(folderPath)) {
                favoriteFolders.add(folderPath);
                favoriteFoldersComboBox.addItem(folderPath); // UI 업데이트
                saveFavoriteFolders(); // 파일에 저장
                JOptionPane.showMessageDialog(this, "즐겨찾기 폴더가 추가되었습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "이미 즐겨찾기에 있는 폴더입니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // 즐겨찾기 폴더 제거
    private void removeFavoriteFolder() {
        String selectedFavorite = (String) favoriteFoldersComboBox.getSelectedItem();
        if (selectedFavorite != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "정말로 '" + selectedFavorite + "' 폴더를 즐겨찾기에서 제거하시겠습니까?",
                    "즐겨찾기 제거 확인", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                favoriteFolders.remove(selectedFavorite);
                favoriteFoldersComboBox.removeItem(selectedFavorite); // UI 업데이트
                saveFavoriteFolders(); // 파일에 저장
                JOptionPane.showMessageDialog(this, "즐겨찾기 폴더가 제거되었습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "제거할 즐겨찾기 폴더를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 즐겨찾기 폴더를 파일에서 불러오기
    private List<String> loadFavoriteFolders() {
        List<String> loadedFolders = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(FAVORITES_FILE), StandardCharsets.UTF_8)) {
            loadedFolders = lines.collect(Collectors.toList());
        } catch (IOException e) {
            // 파일이 없거나 읽을 수 없는 경우 (첫 실행 등), 메시지 출력
            System.out.println("즐겨찾기 파일(" + FAVORITES_FILE + ")을 찾을 수 없거나 읽을 수 없습니다. 새 파일을 생성합니다.");
        }
        return loadedFolders;
    }

    // 즐겨찾기 폴더를 파일에 저장
    private void saveFavoriteFolders() {
        try {
            Files.write(Paths.get(FAVORITES_FILE), favoriteFolders, StandardCharsets.UTF_8);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "즐겨찾기 폴더 저장 중 오류가 발생했습니다: " + e.getMessage(), "오류",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileUrlGeneratorGUI());
    }
}