(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MovieSuccessLogDialogController', MovieSuccessLogDialogController);

    MovieSuccessLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MovieSuccessLog'];

    function MovieSuccessLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MovieSuccessLog) {
        var vm = this;

        vm.movieSuccessLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.movieSuccessLog.id !== null) {
                MovieSuccessLog.update(vm.movieSuccessLog, onSaveSuccess, onSaveError);
            } else {
                MovieSuccessLog.save(vm.movieSuccessLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:movieSuccessLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
