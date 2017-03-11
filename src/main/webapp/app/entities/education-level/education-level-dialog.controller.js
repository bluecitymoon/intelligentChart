(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('EducationLevelDialogController', EducationLevelDialogController);

    EducationLevelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EducationLevel'];

    function EducationLevelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EducationLevel) {
        var vm = this;

        vm.educationLevel = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.educationLevel.id !== null) {
                EducationLevel.update(vm.educationLevel, onSaveSuccess, onSaveError);
            } else {
                EducationLevel.save(vm.educationLevel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:educationLevelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
