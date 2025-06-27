import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GitAutomationGUI extends JFrame {

    private JTextArea outputTextArea;
    private JTextField repoPathField;
    private JComboBox<String> favoriteReposComboBox; // 즐겨찾기 Git 저장소 드롭다운
    private List<String> favoriteRepos; // 즐겨찾기 Git 저장소 경로 저장 리스트
    private final String REPOS_FILE = "git_repos.txt"; // 즐겨찾기 저장 파일

    private JButton selectRepoButton;
    private JButton pullButton;
    private JButton pushButton;
    private JTextField commitMessageField;

    public GitAutomationGUI() {
        setTitle("Git Pull/Push 자동화 도구 (by Gemini AI)");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- 컴포넌트 초기화 ---
        repoPathField = new JTextField("현재 Git 저장소를 선택하세요.", 40);
        repoPathField.setEditable(false);
        selectRepoButton = new JButton("저장소 선택");

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Git 명령어 출력"));

        pullButton = new JButton("Git Pull (당겨오기)");
        pushButton = new JButton("Git Push (올리기)");
        commitMessageField = new JTextField("커밋 메시지를 입력하세요 (예: Feat: 새로운 기능 추가)");

        // 즐겨찾기 초기화
        favoriteRepos = loadFavoriteRepositories();
        favoriteReposComboBox = new JComboBox<>(favoriteRepos.toArray(new String[0]));
        favoriteReposComboBox.setPreferredSize(new Dimension(350, 28));

        JButton addFavoriteButton = new JButton("즐겨찾기 추가");
        JButton removeFavoriteButton = new JButton("즐겨찾기 제거");

        // --- 레이아웃 설정 ---
        setLayout(new BorderLayout(10, 10));

        // 상단 경로 및 선택 버튼 패널
        JPanel pathSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pathSelectionPanel.add(new JLabel("현재 저장소:"));
        pathSelectionPanel.add(repoPathField);
        pathSelectionPanel.add(selectRepoButton);

        // 즐겨찾기 패널
        JPanel favoritePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        favoritePanel.setBorder(BorderFactory.createTitledBorder("즐겨찾는 Git 저장소"));
        favoritePanel.add(favoriteReposComboBox);
        favoritePanel.add(addFavoriteButton);
        favoritePanel.add(removeFavoriteButton);

        JPanel topControlPanel = new JPanel(new BorderLayout());
        topControlPanel.add(pathSelectionPanel, BorderLayout.NORTH);
        topControlPanel.add(favoritePanel, BorderLayout.CENTER);
        add(topControlPanel, BorderLayout.NORTH);

        // 중앙 출력 영역
        add(scrollPane, BorderLayout.CENTER);

        // 하단 Git 명령어 버튼 및 커밋 메시지 패널
        JPanel gitActionsPanel = new JPanel(new BorderLayout(5, 5));
        gitActionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // Pull, Push 두 버튼
        buttonsPanel.add(pullButton);
        buttonsPanel.add(pushButton);

        JPanel commitPanel = new JPanel(new BorderLayout());
        commitPanel.add(new JLabel("커밋 메시지: "), BorderLayout.WEST);
        commitPanel.add(commitMessageField, BorderLayout.CENTER);

        gitActionsPanel.add(buttonsPanel, BorderLayout.NORTH);
        gitActionsPanel.add(commitPanel, BorderLayout.CENTER);
        add(gitActionsPanel, BorderLayout.SOUTH);

        // --- 이벤트 리스너 ---
        selectRepoButton.addActionListener(e -> selectGitRepository());
        pullButton.addActionListener(e -> executeGitCommand("pull"));
        pushButton.addActionListener(e -> {
            String commitMessage = commitMessageField.getText().trim();
            if (commitMessage.isEmpty() || commitMessage.equals("커밋 메시지를 입력하세요 (예: Feat: 새로운 기능 추가)")) {
                JOptionPane.showMessageDialog(this, "커밋 메시지를 입력해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }
            executeGitCommand("add_commit_push", commitMessage);
        });

        addFavoriteButton.addActionListener(e -> addFavoriteRepository());
        removeFavoriteButton.addActionListener(e -> removeFavoriteRepository());
        favoriteReposComboBox.addActionListener(e -> {
            String selectedRepo = (String) favoriteReposComboBox.getSelectedItem();
            if (selectedRepo != null && !selectedRepo.isEmpty()) {
                File selectedFile = new File(selectedRepo);
                if (selectedFile.isDirectory() && new File(selectedFile, ".git").isDirectory()) {
                    repoPathField.setText(selectedRepo);
                    outputTextArea.append("즐겨찾기 저장소 선택됨: " + selectedRepo + "\n");
                    outputTextArea.append(
                            "--------------------------------------------------------------------------------\n");
                } else {
                    JOptionPane.showMessageDialog(this, "선택된 즐겨찾기 저장소가 유효하지 않거나 존재하지 않습니다.\n" +
                            "목록에서 제거하는 것을 권장합니다.", "경고", JOptionPane.WARNING_MESSAGE);
                    // 유효하지 않은 항목이 드롭다운에 계속 남아있지 않도록 할 수도 있습니다. (선택 사항)
                }
            }
        });

        setVisible(true);
    }

    // Git 저장소 선택 메서드
    private void selectGitRepository() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Git 저장소 폴더 선택 ('.git' 폴더를 포함하는 상위 폴더)");
        fileChooser.setApproveButtonText("선택");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            // .git 폴더 존재 여부 확인으로 Git 저장소인지 검증
            if (new File(selectedFolder, ".git").isDirectory()) {
                repoPathField.setText(selectedFolder.getAbsolutePath());
                outputTextArea.append("Git 저장소 선택: " + selectedFolder.getAbsolutePath() + "\n");
                outputTextArea
                        .append("--------------------------------------------------------------------------------\n");
            } else {
                JOptionPane.showMessageDialog(this, "선택된 폴더는 Git 저장소가 아닌 것 같습니다. (.git 폴더 없음)", "경고",
                        JOptionPane.WARNING_MESSAGE);
                repoPathField.setText("Git 저장소 경로를 선택하세요.");
            }
        }
    }

    // 즐겨찾기 저장소 추가
    private void addFavoriteRepository() {
        String currentRepoPath = repoPathField.getText();
        if (currentRepoPath.isEmpty() || currentRepoPath.equals("현재 Git 저장소를 선택하세요.")) {
            JOptionPane.showMessageDialog(this, "먼저 현재 작업할 Git 저장소를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File currentRepo = new File(currentRepoPath);
        if (currentRepo.isDirectory() && new File(currentRepo, ".git").isDirectory()) {
            if (!favoriteRepos.contains(currentRepoPath)) {
                favoriteRepos.add(currentRepoPath);
                favoriteReposComboBox.addItem(currentRepoPath);
                saveFavoriteRepositories();
                JOptionPane.showMessageDialog(this, "현재 저장소 (" + currentRepoPath + ")가 즐겨찾기에 추가되었습니다.", "정보",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "이미 즐겨찾기에 있는 저장소입니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "선택된 경로가 유효한 Git 저장소가 아닙니다.", "경고", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 즐겨찾기 저장소 제거
    private void removeFavoriteRepository() {
        String selectedFavorite = (String) favoriteReposComboBox.getSelectedItem();
        if (selectedFavorite != null && !selectedFavorite.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "정말로 '" + selectedFavorite + "' 저장소를 즐겨찾기에서 제거하시겠습니까?",
                    "즐겨찾기 제거 확인", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                favoriteRepos.remove(selectedFavorite);
                favoriteReposComboBox.removeItem(selectedFavorite);
                saveFavoriteRepositories();
                JOptionPane.showMessageDialog(this, "즐겨찾기 저장소가 제거되었습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "제거할 즐겨찾기 저장소를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 즐겨찾기 저장소를 파일에서 불러오기
    private List<String> loadFavoriteRepositories() {
        List<String> loadedRepos = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(REPOS_FILE), StandardCharsets.UTF_8)) {
            loadedRepos = lines.collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("즐겨찾기 저장소 파일(" + REPOS_FILE + ")을 찾을 수 없거나 읽을 수 없습니다. 새 파일을 생성합니다.");
        }
        return loadedRepos;
    }

    // 즐겨찾기 저장소를 파일에 저장
    private void saveFavoriteRepositories() {
        try {
            Files.write(Paths.get(REPOS_FILE), favoriteRepos, StandardCharsets.UTF_8);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "즐겨찾기 저장소 저장 중 오류가 발생했습니다: " + e.getMessage(), "오류",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Git 명령어 실행 메서드 (백그라운드에서 실행)
    private void executeGitCommand(String commandType) {
        executeGitCommand(commandType, null); // 메시지 없이 호출
    }

    private void executeGitCommand(String commandType, String commitMessage) {
        String repoPath = repoPathField.getText();
        if (repoPath.isEmpty() || repoPath.equals("현재 Git 저장소를 선택하세요.")) {
            JOptionPane.showMessageDialog(this, "먼저 Git 저장소 폴더를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File workingDirectory = new File(repoPath);
        if (!workingDirectory.isDirectory() || !new File(workingDirectory, ".git").isDirectory()) {
            JOptionPane.showMessageDialog(this, "선택된 경로가 유효한 Git 저장소가 아닙니다: " + repoPath, "오류",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        outputTextArea.setText(""); // 기존 출력 지우기
        outputTextArea.append("▶ Git " + commandType.replace("_", " ").toUpperCase() + " 명령 실행 중...\n");
        outputTextArea.append("작업 디렉토리: " + repoPath + "\n");
        outputTextArea.append("--------------------------------------------------------------------------------\n\n");

        new SwingWorker<String, String>() {
            @Override
            protected String doInBackground() throws Exception {
                List<String> commands = new ArrayList<>();
                String finalResult = "";

                if ("pull".equals(commandType)) {
                    commands.add("git");
                    commands.add("pull");
                    finalResult = executeSingleCommand(commands, workingDirectory);
                } else if ("add_commit_push".equals(commandType)) {
                    // 1. git add .
                    publish("--- git add . 실행 중 ---\n");
                    commands.add("git");
                    commands.add("add");
                    commands.add(".");
                    String addResult = executeSingleCommand(commands, workingDirectory);
                    publish(addResult + "\n");
                    commands.clear(); // 명령어 리스트 초기화

                    // 2. git commit -m "message"
                    publish("--- git commit -m \"" + commitMessage + "\" 실행 중 ---\n");
                    commands.add("git");
                    commands.add("commit");
                    commands.add("-m");
                    commands.add(commitMessage);
                    String commitResult = executeSingleCommand(commands, workingDirectory);
                    publish(commitResult + "\n");
                    commands.clear(); // 명령어 리스트 초기화

                    // 커밋할 내용이 없는지 확인
                    if (commitResult.contains("nothing to commit")
                            || commitResult.contains("no changes added to commit")) {
                        publish("\n>>> 커밋할 내용이 없어서 푸쉬를 건너뜜니다. <<<\n");
                        return "완료 (커밋 없음)";
                    }

                    // 3. git push
                    publish("--- git push 실행 중 ---\n");
                    commands.add("git");
                    commands.add("push");
                    finalResult = executeSingleCommand(commands, workingDirectory);
                } else {
                    publish("알 수 없는 Git 명령어 타입: " + commandType + "\n");
                    return "오류";
                }
                return finalResult;
            }

            // 개별 Git 명령어를 실행하고 결과를 StringBuilder로 반환
            // 이 메서드는 doInBackground 내부에서만 호출되어야 함 (UI 스레드와 무관)
            private String executeSingleCommand(List<String> commands, File workingDir)
                    throws IOException, InterruptedException {
                ProcessBuilder pb = new ProcessBuilder(commands);
                pb.directory(workingDir);
                pb.redirectErrorStream(true); // 에러 스트림도 출력으로 합침

                Process process = pb.start();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)); // UTF-8로 읽기
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                    publish(line + "\n"); // 진행 상황을 GUI에 실시간으로 표시
                }
                int exitCode = process.waitFor();
                output.append("\n[Exit Code: ").append(exitCode).append("]\n");
                return output.toString();
            }

            @Override
            protected void process(List<String> chunks) {
                // publish()를 통해 전달된 데이터를 GUI에 업데이트
                for (String text : chunks) {
                    outputTextArea.append(text);
                }
            }

            @Override
            protected void done() {
                try {
                    get(); // doInBackground의 최종 결과 가져오기 (여기서는 값을 사용하지 않고 예외만 처리)
                    outputTextArea.append(
                            "\n--------------------------------------------------------------------------------\n");
                    outputTextArea.append("▶ Git 명령 실행 완료.\n");
                } catch (InterruptedException | ExecutionException ex) {
                    outputTextArea.append(
                            "\n--------------------------------------------------------------------------------\n");
                    outputTextArea.append("▶ 오류 발생: " + ex.getMessage() + "\n");
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(GitAutomationGUI.this,
                            "Git 명령어 실행 중 오류가 발생했습니다.\n자세한 내용은 출력창을 확인해주세요.",
                            "오류", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // 버튼 활성화 등 필요한 후처리
                }
            }
        }.execute(); // SwingWorker 실행
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GitAutomationGUI());
    }
}