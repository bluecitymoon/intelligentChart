(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('EgeLevelDialogController', EgeLevelDialogController);

    EgeLevelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EgeLevel'];

    function EgeLevelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EgeLevel) {
        var vm = this;

        vm.egeLevel = entity;
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
            if (vm.egeLevel.id !== null) {
                EgeLevel.update(vm.egeLevel, onSaveSuccess, onSaveError);
            } else {
                EgeLevel.save(vm.egeLevel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:egeLevelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
