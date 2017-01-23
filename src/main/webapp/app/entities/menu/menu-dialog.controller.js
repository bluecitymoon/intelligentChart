(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MenuDialogController', MenuDialogController);

    MenuDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Menu'];

    function MenuDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Menu) {
        var vm = this;

        vm.menu = entity;
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
            if (vm.menu.id !== null) {
                Menu.update(vm.menu, onSaveSuccess, onSaveError);
            } else {
                Menu.save(vm.menu, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:menuUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
