(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MenuGroupDialogController', MenuGroupDialogController);

    MenuGroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MenuGroup', 'Menu'];

    function MenuGroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MenuGroup, Menu) {
        var vm = this;

        vm.menuGroup = entity;
        vm.clear = clear;
        vm.save = save;
        vm.menus = Menu.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.menuGroup.id !== null) {
                MenuGroup.update(vm.menuGroup, onSaveSuccess, onSaveError);
            } else {
                MenuGroup.save(vm.menuGroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:menuGroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
